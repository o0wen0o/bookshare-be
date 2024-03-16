package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.Roles;
import com.fyp.bookshare.mapper.admin.RolesMapper;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.service.admin.IRolesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
@Slf4j
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Roles> implements IRolesService {


    @Override
    public IPage<Roles> getRoles(Page<Roles> page, String filter) {
        QueryWrapper<Roles> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("name", filter);
        }

        return this.baseMapper.selectPage(page, wrapper);
    }
}
