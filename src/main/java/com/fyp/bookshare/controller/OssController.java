package com.fyp.bookshare.controller;

import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.OssPolicyResult;
import com.fyp.bookshare.service.impl.OssServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.function.Supplier;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 *
 * '/aliyun/oss/policy'
 */
@Controller
@Api(tags = "OssController", description = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {

    @Autowired
    private OssServiceImpl ossService;

    /**
     * oss上传签名生成
     *
     * @return
     */
    @ApiOperation(value = "oss上传签名生成")
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public RestBean<OssPolicyResult> policy() {
        OssPolicyResult policy = ossService.policy();
        return policy != null ? RestBean.success(policy) : RestBean.failure(400, "获取签名失败");
    }
}
