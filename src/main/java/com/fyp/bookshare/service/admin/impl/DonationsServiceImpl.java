package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.Donations;
import com.fyp.bookshare.mapper.admin.DonationsMapper;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IDonationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class DonationsServiceImpl extends ServiceImpl<DonationsMapper, Donations> implements IDonationsService {

    @Override
    public IPage<Donations> getDonations(Page<Donations> page, String filter) {
        QueryWrapper<Donations> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("contribution_date", filter)
                    .or().like("user_id", filter)
                    .or().like("fundraising_project_id", filter);
        }

        return this.baseMapper.selectPage(page, wrapper);
    }
}
