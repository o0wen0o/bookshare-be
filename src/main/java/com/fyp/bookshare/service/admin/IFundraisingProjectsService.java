package com.fyp.bookshare.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.FundraisingProjects;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IFundraisingProjectsService extends IService<FundraisingProjects> {

    IPage<FundraisingProjects> getFundraisingProjects(Page<FundraisingProjects> page, String filter);
}
