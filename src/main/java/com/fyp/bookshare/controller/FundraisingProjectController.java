package com.fyp.bookshare.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.FundraisingProjects;
import com.fyp.bookshare.service.admin.IFundraisingProjectsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author o0wen0o
 * @create 2024-04-11 10:02 AM
 */
@RestController
@RequestMapping("/api/fundraising-project")
public class FundraisingProjectController {

    @Resource
    IFundraisingProjectsService fundraisingProjectsService;

    @GetMapping("/getFundraisingProjects")
    @Operation(summary = "Get a list of fundraising projects")
    public RestBean<IPage<FundraisingProjects>> getFundraisingProjects(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "10"));
        String filter = params.getOrDefault("filter", "");

        Page<FundraisingProjects> page = new Page<>(current, size);
        IPage<FundraisingProjects> fundraisingProjects = fundraisingProjectsService.getFundraisingProjects(page, filter);
        return RestBean.success(fundraisingProjects);
    }
}
