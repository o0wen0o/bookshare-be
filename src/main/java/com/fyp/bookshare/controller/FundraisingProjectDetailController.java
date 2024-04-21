package com.fyp.bookshare.controller;

import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.FundraisingProjectDetailDTO;
import com.fyp.bookshare.pojo.FundraisingProjectProgress;
import com.fyp.bookshare.service.admin.IFundraisingProjectProgressService;
import com.fyp.bookshare.service.admin.IFundraisingProjectsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author o0wen0o
 * @create 2024-04-11 11:20 PM
 */
@RestController
@RequestMapping("/api/fundraising-project-detail")
public class FundraisingProjectDetailController {

    @Resource
    IFundraisingProjectsService fundraisingProjectsService;

    @Resource
    IFundraisingProjectProgressService fundraisingProjectProgressService;

    @GetMapping("/getFundraisingProjectDetail/{fundraisingProjectId}")
    @Operation(summary = "Get fundraising project detail")
    public RestBean<FundraisingProjectDetailDTO> getFundraisingProjectDetail(@PathVariable Integer fundraisingProjectId) {
        FundraisingProjectDetailDTO fundraisingProjectDetailDTO =
                fundraisingProjectsService.getFundraisingProjectDetail(fundraisingProjectId);
        return RestBean.success(fundraisingProjectDetailDTO);
    }

    @GetMapping("/getFundraisingProjectProgressesByProjectId/{fundraisingProjectId}")
    @Operation(summary = "Get fundraising project progresses by fundraising project id")
    public RestBean<List<FundraisingProjectProgress>> getFundraisingProjectProgressesByProjectId(@PathVariable Integer fundraisingProjectId) {
        List<FundraisingProjectProgress> fundraisingProjectProgresses =
                fundraisingProjectProgressService.getFundraisingProjectProgressesByProjectId(fundraisingProjectId);
        return RestBean.success(fundraisingProjectProgresses);
    }
}
