package com.fyp.bookshare.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.pojo.FundraisingOrganizers;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
public interface IFundraisingOrganizersService extends IService<FundraisingOrganizers> {

    IPage<FundraisingOrganizers> getFundraisingOrganizers(Page<FundraisingOrganizers> page, String filter);
}
