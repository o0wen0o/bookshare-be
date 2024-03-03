package com.fyp.bookshare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fyp.bookshare.entity.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper extends BaseMapper<UserDTO> {

    UserDTO getUserByEmail(@Param("email") String email);
}
