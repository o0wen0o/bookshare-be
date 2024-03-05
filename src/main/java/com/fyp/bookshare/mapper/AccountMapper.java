package com.fyp.bookshare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyp.bookshare.entity.dto.UserDTO;
import com.fyp.bookshare.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper extends BaseMapper<Users> {

    UserDTO getUserByEmail(@Param("email") String email);
}
