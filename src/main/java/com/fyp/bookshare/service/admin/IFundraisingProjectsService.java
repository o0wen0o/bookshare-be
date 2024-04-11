package com.fyp.bookshare.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.dto.FundraisingProjectSelectionsDTO;
import com.fyp.bookshare.pojo.Books;
import com.fyp.bookshare.pojo.FundraisingProjects;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

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

    IPage<FundraisingProjectSelectionsDTO> getFundraisingProjectSelections(Page<Books> page, String filter);

    Boolean addFundraisingProject(FundraisingProjects fundraisingProject, MultipartFile image);

    Boolean updateFundraisingProject(Integer id, FundraisingProjects fundraisingProject, MultipartFile image);
}
