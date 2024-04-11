package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.mapper.admin.FundraisingProjectProgressMapper;
import com.fyp.bookshare.pojo.FundraisingProjectProgress;
import com.fyp.bookshare.service.admin.IFundraisingProjectProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 服务类
 * </p>
 *
 * @author o0wen0o
 * @create 2024-04-11 11:16 AM
 */
@Service
public class FundraisingProjectProgressServiceImpl extends ServiceImpl<FundraisingProjectProgressMapper, FundraisingProjectProgress> implements IFundraisingProjectProgressService {

    @Override
    public IPage<FundraisingProjectProgress> getFundraisingProjectProgresses(Page<FundraisingProjectProgress> page, String filter) {
        return null;
    }
}
