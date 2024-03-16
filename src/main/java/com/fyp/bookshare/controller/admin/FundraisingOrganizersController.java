package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.FundraisingOrganizers;
import com.fyp.bookshare.service.admin.IFundraisingOrganizersService;
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
 * @since 2024-02-28
 */
@RestController
@RequestMapping("/api/fundraising-organizers")
public class FundraisingOrganizersController {

    @Resource
    IFundraisingOrganizersService fundraisingOrganizersService;

    @GetMapping("/")
    @Operation(summary = "Get a list of fundraisingOrganizers")
    public RestBean<IPage<FundraisingOrganizers>> getFundraisingOrganizers(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<FundraisingOrganizers> page = new Page<>(current, size);
        IPage<FundraisingOrganizers> fundraisingOrganizers = fundraisingOrganizersService.getFundraisingOrganizers(page, filter);
        return RestBean.success(fundraisingOrganizers);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a fundraisingOrganizer by its ID")
    public RestBean<FundraisingOrganizers> getFundraisingOrganizerById(@PathVariable Long id) {
        FundraisingOrganizers fundraisingOrganizer = fundraisingOrganizersService.getById(id);
        if (fundraisingOrganizer != null) {
            return RestBean.success(fundraisingOrganizer);
        } else {
            return RestBean.failure(400, "FundraisingOrganizer not found");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Add a new fundraisingOrganizer")
    public RestBean<Void> addFundraisingOrganizer(@RequestBody FundraisingOrganizers fundraisingOrganizer) {
        return messageHandle(() -> fundraisingOrganizersService.save(fundraisingOrganizer), "Failed to add the fundraisingOrganizer");
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Update an existing fundraisingOrganizer")
    public RestBean<Void> updateFundraisingOrganizer(@PathVariable Integer id, @RequestBody FundraisingOrganizers fundraisingOrganizer) {
        fundraisingOrganizer.setId(id);
        return messageHandle(() -> fundraisingOrganizersService.updateById(fundraisingOrganizer), "Failed to update the fundraisingOrganizer");
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "Delete a fundraisingOrganizer")
    public RestBean<Void> deleteFundraisingOrganizers(@PathVariable List<Integer> ids) {
        return messageHandle(() -> fundraisingOrganizersService.removeByIds(ids), "Failed to delete the fundraisingOrganizer");
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
