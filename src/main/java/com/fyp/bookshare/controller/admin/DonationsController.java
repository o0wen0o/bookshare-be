package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.Donations;
import com.fyp.bookshare.service.admin.IDonationsService;
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
@RequestMapping("/api/donations")
public class DonationsController {

    @Resource
    IDonationsService donationsService;

    @GetMapping("/")
    @Operation(summary = "Get a list of donations")
    public RestBean<IPage<Donations>> getDonations(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<Donations> page = new Page<>(current, size);
        IPage<Donations> donations = donationsService.getDonations(page, filter);
        return RestBean.success(donations);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a donation by its ID")
    public RestBean<Donations> getDonationById(@PathVariable Long id) {
        Donations donation = donationsService.getById(id);
        if (donation != null) {
            return RestBean.success(donation);
        } else {
            return RestBean.failure(400, "Donation not found");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Add a new donation")
    public RestBean<Void> addDonation(@RequestBody Donations donation) {
        return messageHandle(() -> donationsService.save(donation), "Failed to add the donation");
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Update an existing donation")
    public RestBean<Void> updateDonation(@PathVariable Integer id, @RequestBody Donations donation) {
        donation.setId(id);
        return messageHandle(() -> donationsService.updateById(donation), "Failed to update the donation");
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "Delete a donation")
    public RestBean<Void> deleteDonations(@PathVariable List<Integer> ids) {
        return messageHandle(() -> donationsService.removeByIds(ids), "Failed to delete the donation");
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
