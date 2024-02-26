package com.fyp.bookshare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyp.bookshare.entity.dto.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
