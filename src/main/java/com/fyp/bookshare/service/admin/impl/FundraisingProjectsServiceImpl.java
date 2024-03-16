package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.FundraisingProjects;
import com.fyp.bookshare.mapper.admin.FundraisingProjectsMapper;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IFundraisingProjectsService;
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
public class FundraisingProjectsServiceImpl extends ServiceImpl<FundraisingProjectsMapper, FundraisingProjects> implements IFundraisingProjectsService {

    @Override
    public IPage<FundraisingProjects> getFundraisingProjects(Page<FundraisingProjects> page, String filter) {
        QueryWrapper<FundraisingProjects> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("project_name", filter)
                    .or().like("description", filter)
                    .or().like("start_date", filter)
                    .or().like("end_date", filter)
                    .or().like("status", filter)
                    .or().like("created_date", filter);
        }

        return this.baseMapper.selectPage(page, wrapper);
    }
}
