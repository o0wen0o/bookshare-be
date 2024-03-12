package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.Users;
import com.fyp.bookshare.mapper.admin.UsersMapper;
import com.fyp.bookshare.service.admin.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Override
    public IPage<Users> getUsers(Page<Users> page, String filter) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("username", filter)
                    .or().like("email", filter)
                    .or().like("phone_number", filter)
                    .or().like("created_date", filter);
        }

        return this.baseMapper.selectPage(page, wrapper);
    }
}
