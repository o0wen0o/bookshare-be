package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.OrganizerSelectionsDTO;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.FundraisingProjects;
import com.fyp.bookshare.service.admin.IFundraisingOrganizersService;
import com.fyp.bookshare.service.admin.IFundraisingProjectsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/api/fundraising-projects")
public class FundraisingProjectsController {

    @Resource
    IFundraisingProjectsService fundraisingProjectsService;

    @Resource
    IFundraisingOrganizersService fundraisingOrganizersService;

    @GetMapping("/")
    @Operation(summary = "Get a list of fundraisingProjects")
    public RestBean<IPage<FundraisingProjects>> getFundraisingProjects(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<FundraisingProjects> page = new Page<>(current, size);
        IPage<FundraisingProjects> fundraisingProjects = fundraisingProjectsService.getFundraisingProjects(page, filter);
        return RestBean.success(fundraisingProjects);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a fundraisingProject by its ID")
    public RestBean<FundraisingProjects> getFundraisingProjectById(@PathVariable Long id) {
        FundraisingProjects fundraisingProject = fundraisingProjectsService.getById(id);
        if (fundraisingProject != null) {
            return RestBean.success(fundraisingProject);
        } else {
            return RestBean.failure(400, "FundraisingProject not found");
        }
    }

    @GetMapping("/getOrganizerSelections")
    @Operation(summary = "Get a list of organizer selections")
    public RestBean<IPage<OrganizerSelectionsDTO>> getOrganizerSelections(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<Books> page = new Page<>(current, size);

        IPage<OrganizerSelectionsDTO> bookSelectionsDTOs = fundraisingOrganizersService.getOrganizerSelections(page, filter);
        return RestBean.success(bookSelectionsDTOs);
    }

    @PostMapping("/create")
    @Operation(summary = "Add a new fundraisingProject")
    public RestBean<Void> addFundraisingProject(@ModelAttribute FundraisingProjects fundraisingProject, MultipartFile image) {
        return messageHandle(() -> fundraisingProjectsService.addFundraisingProject(fundraisingProject, image), "Failed to add the fundraisingProject");
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Update an existing fundraisingProject")
    public RestBean<Void> updateFundraisingProject(@PathVariable Integer id, @ModelAttribute FundraisingProjects fundraisingProject, MultipartFile image) {
        return messageHandle(() -> fundraisingProjectsService.updateFundraisingProject(id, fundraisingProject, image), "Failed to update the fundraisingProject");
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "Delete a fundraisingProject")
    public RestBean<Void> deleteFundraisingProjects(@PathVariable List<Integer> ids) {
        return messageHandle(() -> fundraisingProjectsService.removeByIds(ids), "Failed to delete the fundraisingProject");
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
