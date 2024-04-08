package com.fyp.bookshare.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.DonationDTO;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.Donations;
import com.fyp.bookshare.service.admin.IDonationsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author o0wen0o
 * @create 2024-04-08 11:32 AM
 */
@RestController
@RequestMapping("/api/profile-donation")
public class ProfileDonation {

    @Resource
    IDonationsService donationsService;

    @GetMapping("/getDonationsByUserId")
    @Operation(summary = "Get a list of donations by user id")
    public RestBean<IPage<DonationDTO>> getDonationsByUserId(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "10"));
        Integer userId = Integer.valueOf(params.getOrDefault("userId", ""));

        Page<DonationDTO> page = new Page<>(current, size);
        IPage<DonationDTO> donations = donationsService.getDonationsByUserId(page, userId);
        return RestBean.success(donations);
    }

}
