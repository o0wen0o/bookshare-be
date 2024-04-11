package com.fyp.bookshare.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.FundraisingProjectProgress;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @create 2024-04-11 11:09 AM
 */
public interface IFundraisingProjectProgressService extends IService<FundraisingProjectProgress> {
    IPage<FundraisingProjectProgress> getFundraisingProjectProgresses(Page<FundraisingProjectProgress> page, String filter);
}
