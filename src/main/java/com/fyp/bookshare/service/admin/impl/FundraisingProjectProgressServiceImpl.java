package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.mapper.admin.FundraisingProjectProgressMapper;
import com.fyp.bookshare.pojo.FundraisingProjectProgress;
import com.fyp.bookshare.service.admin.IFundraisingProjectProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.service.impl.OssServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Resource
    private FundraisingProjectProgressMapper fundraisingProjectProgressMapper;

    @Resource
    private OssServiceImpl ossService;

    @Override
    public IPage<FundraisingProjectProgress> getFundraisingProjectProgresses(Page<FundraisingProjectProgress> page, String filter) {
        QueryWrapper<FundraisingProjectProgress> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("title", filter)
                    .or().like("description", filter)
                    .or().like("updated_date", filter)
                    .or().like("created_date", filter);
        }

        return fundraisingProjectProgressMapper.selectPage(page, wrapper);
    }

    @Override
    public List<FundraisingProjectProgress> getFundraisingProjectProgressesByProjectId(Integer fundraisingProjectId) {
        QueryWrapper<FundraisingProjectProgress> wrapper = new QueryWrapper<>();

        wrapper.eq("fundraising_project_id", fundraisingProjectId);
        wrapper.orderByDesc("updated_date");

        return fundraisingProjectProgressMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public Boolean addFundraisingProject(FundraisingProjectProgress fundraisingProjectProgress, MultipartFile image) {
        // Save the fundraising project without the image first to generate its ID
        boolean isSave = this.save(fundraisingProjectProgress);

        if (isSave && image != null && !image.isEmpty()) {
            String imageUrl = "fundraisingProjectProgress/image/" + ossService.generateFileName(fundraisingProjectProgress.getId(), image); // Generate a unique file name
            ossService.uploadImage(image, imageUrl); // Upload image to oss

            fundraisingProjectProgress.setImgUrl(imageUrl);
            return this.updateById(fundraisingProjectProgress);
        }

        return isSave;
    }

    @Override
    @Transactional
    public Boolean updateFundraisingProject(Integer id, FundraisingProjectProgress fundraisingProjectProgress, MultipartFile image) {
        fundraisingProjectProgress.setId(id);

        // Check if an image is provided
        if (image != null && !image.isEmpty()) {
            String imageUrl = "fundraisingProjectProgress/image/" + ossService.generateFileName(id, image); // Generate a unique file name
            ossService.uploadImage(image, imageUrl); // Upload image to oss
            fundraisingProjectProgress.setImgUrl(imageUrl);
        }

        return this.updateById(fundraisingProjectProgress);
    }
}
