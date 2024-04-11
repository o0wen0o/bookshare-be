package com.fyp.bookshare.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.FundraisingProjectDetailDTO;
import com.fyp.bookshare.entity.dto.FundraisingProjectSelectionsDTO;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.FundraisingProjects;
import com.fyp.bookshare.mapper.admin.FundraisingProjectsMapper;
import com.fyp.bookshare.service.admin.IFundraisingProjectsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.bookshare.service.impl.OssServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Service
public class FundraisingProjectsServiceImpl extends ServiceImpl<FundraisingProjectsMapper, FundraisingProjects> implements IFundraisingProjectsService {

    @Resource
    private FundraisingProjectsMapper fundraisingProjectsMapper;

    @Resource
    OssServiceImpl ossService;

    @Override
    public IPage<FundraisingProjects> getFundraisingProjects(Page<FundraisingProjects> page, String filter) {
        QueryWrapper<FundraisingProjects> wrapper = new QueryWrapper<>();

        if (filter != null && !filter.isEmpty()) {
            wrapper.like("project_name", filter)
                    .or().like("description", filter)
                    .or().like("start_date", filter)
                    .or().like("end_date", filter)
                    .or().like("status", filter)
                    .or().like("created_date", filter);
        }

        return fundraisingProjectsMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<FundraisingProjectSelectionsDTO> getFundraisingProjectSelections(Page<Books> page, String filter) {
        return fundraisingProjectsMapper.selectFundraisingProjectSelectionsWithPagination(page, filter);
    }

    @Override
    public FundraisingProjectDetailDTO getFundraisingProjectDetail(Integer fundraisingProjectId) {
        return fundraisingProjectsMapper.getFundraisingProjectDetail(fundraisingProjectId);
    }

    @Override
    @Transactional
    public Boolean addFundraisingProject(FundraisingProjects fundraisingProject, MultipartFile image) {
        // Save the fundraisingProject without the image first to generate the user ID
        boolean fundraisingProjectSaved = this.save(fundraisingProject);

        // Proceed only if the fundraisingProject was successfully saved
        if (fundraisingProjectSaved && image != null && !image.isEmpty()) {
            String imageUrl = "fundraisingProjects/image/" + ossService.generateFileName(fundraisingProject.getId(), image); // Generate a unique file name
            ossService.uploadImage(image, imageUrl); // Upload image to oss

            // Update the fundraisingProject with the image URL
            fundraisingProject.setImgUrl(imageUrl);
            return this.updateById(fundraisingProject);
        }

        return fundraisingProjectSaved;
    }

    @Override
    @Transactional
    public Boolean updateFundraisingProject(Integer id, FundraisingProjects fundraisingProject, MultipartFile image) {
        // Fetch the existing user to get the current details, especially the password
        FundraisingProjects existingProject = this.getById(id);
        if (existingProject == null) {
            // Handle the case where the user doesn't exist
            throw new IllegalArgumentException("Fundraising project not found");
        }

        fundraisingProject.setId(id);

        // Check if an image is provided
        if (image != null && !image.isEmpty()) {
            String imageUrl = "fundraisingProjects/image/" + ossService.generateFileName(fundraisingProject.getId(), image); // Generate a unique file name
            ossService.uploadImage(image, imageUrl); // Upload image to oss
            fundraisingProject.setImgUrl(imageUrl);
        }

        fundraisingProject.setUpdatedDate(LocalDate.now());

        // Update the user with either the new password or the existing one
        return this.updateById(fundraisingProject);
    }
}
