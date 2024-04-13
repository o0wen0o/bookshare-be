package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.DonationDTO;
import com.fyp.bookshare.pojo.Donations;
import com.fyp.bookshare.mapper.admin.DonationsMapper;
import com.fyp.bookshare.pojo.FundraisingProjects;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IDonationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.service.admin.IFundraisingProjectsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class DonationsServiceImpl extends ServiceImpl<DonationsMapper, Donations> implements IDonationsService {

    @Resource
    DonationsMapper donationsMapper;

    @Resource
    IFundraisingProjectsService fundraisingProjectsService;

    @Override
    public IPage<Donations> getDonations(Page<Donations> page, String filter) {
        QueryWrapper<Donations> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("contribution_date", filter)
                    .or().like("user_id", filter)
                    .or().like("fundraising_project_id", filter);
        }

        return donationsMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<DonationDTO> getDonationsByUserId(Page<DonationDTO> page, Integer userId) {
        return donationsMapper.getDonationsByUserId(page, userId);
    }

    @Override
    @Transactional
    public boolean addDonation(Donations donation) {
        boolean donationSaved = this.save(donation);

        // update fundraising project
        if (donationSaved) {
            QueryWrapper<FundraisingProjects> wrapper = new QueryWrapper<>();
            wrapper.eq("id", donation.getFundraisingProjectId());
            FundraisingProjects fundraisingProject = fundraisingProjectsService.getOne(wrapper);

            boolean updated = false;
            if (fundraisingProject != null) {
                // update amount raised
                BigDecimal addedAmountRaised = fundraisingProject.getAmountRaised().add(donation.getDonationAmount());
                fundraisingProject.setAmountRaised(addedAmountRaised);

                // update donation count
                Integer donationCount = fundraisingProject.getDonationCount() + 1;
                fundraisingProject.setDonationCount(donationCount);

                updated = fundraisingProjectsService.updateById(fundraisingProject);
            }
            return updated;
        }

        return false;
    }
}
