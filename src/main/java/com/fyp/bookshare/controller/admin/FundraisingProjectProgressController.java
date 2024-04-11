package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.FundraisingProjectProgress;
import com.fyp.bookshare.service.admin.IFundraisingProjectProgressService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author o0wen0o
 * @since 2024-04-11
 */
@RestController
@RequestMapping("/api/fundraising-project-progress")
public class FundraisingProjectProgressController {

    @Resource
    IFundraisingProjectProgressService fundraisingProjectProgressService;

    @GetMapping("/")
    @Operation(summary = "Get a list of fundraising project progresses")
    public RestBean<IPage<FundraisingProjectProgress>> getFundraisingProjectProgresses(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<FundraisingProjectProgress> page = new Page<>(current, size);
        IPage<FundraisingProjectProgress> fundraisingProjectProgresses = fundraisingProjectProgressService.getFundraisingProjectProgresses(page, filter);
        return RestBean.success(fundraisingProjectProgresses);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a fundraising project progress by its ID")
    public RestBean<FundraisingProjectProgress> getFundraisingProjectById(@PathVariable Long id) {
        FundraisingProjectProgress fundraisingProjectProgress = fundraisingProjectProgressService.getById(id);
        if (fundraisingProjectProgress != null) {
            return RestBean.success(fundraisingProjectProgress);
        } else {
            return RestBean.failure(400, "Fundraising project progress not found");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Add a new fundraising project progress")
    public RestBean<Void> addFundraisingProject(@RequestBody FundraisingProjectProgress fundraisingProjectProgress) {
        return messageHandle(() -> fundraisingProjectProgressService.save(fundraisingProjectProgress), "Failed to add the fundraising project progress");
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Update an existing fundraising project progress")
    public RestBean<Void> updateFundraisingProject(@PathVariable Integer id, @RequestBody FundraisingProjectProgress fundraisingProjectProgress) {
        fundraisingProjectProgress.setId(id);
        return messageHandle(() -> fundraisingProjectProgressService.updateById(fundraisingProjectProgress), "Failed to update the fundraising project progress");
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "Delete a fundraising project progress")
    public RestBean<Void> deleteFundraisingProjectProgress(@PathVariable List<Integer> ids) {
        return messageHandle(() -> fundraisingProjectProgressService.removeByIds(ids), "Failed to delete the fundraisingProject");
    }

    /**
     * 针对于返回值为String作为错误信息的方法进行统一处理
     *
     * @param action 具体操作
     * @return 响应结果
     */
    private RestBean<Void> messageHandle(BooleanSupplier action, String failureMessage) {
        boolean result = action.getAsBoolean();
        if (result) {
            return RestBean.success();
        } else {
            return RestBean.failure(400, failureMessage);
        }
    }

}
