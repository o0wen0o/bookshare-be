package com.fyp.bookshare.mapper.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.DonationDTO;
import com.fyp.bookshare.pojo.Donations;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface DonationsMapper extends BaseMapper<Donations> {

    IPage<DonationDTO> getDonationsByUserId(Page<DonationDTO> page, Integer userId);
}
