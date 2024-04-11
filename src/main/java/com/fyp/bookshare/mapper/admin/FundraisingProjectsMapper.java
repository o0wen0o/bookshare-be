package com.fyp.bookshare.mapper.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.FundraisingProjectDetailDTO;
import com.fyp.bookshare.entity.dto.FundraisingProjectSelectionsDTO;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.FundraisingProjects;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface FundraisingProjectsMapper extends BaseMapper<FundraisingProjects> {

    IPage<FundraisingProjectSelectionsDTO> selectFundraisingProjectSelectionsWithPagination(Page<Books> page, String filter);

    FundraisingProjectDetailDTO getFundraisingProjectDetail(Integer fundraisingProjectId);
}
