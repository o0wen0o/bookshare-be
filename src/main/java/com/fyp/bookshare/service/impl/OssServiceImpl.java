package com.fyp.bookshare.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.fyp.bookshare.service.OssService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * oss上传管理Service实现类
 * Created on 2018/5/17.
 */
@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Autowired
    private OSSClient ossClient;

    @SneakyThrows
    public void uploadImage(MultipartFile image, String imageUrl) {
        ossClient.putObject(new PutObjectRequest(bucketName, imageUrl, image.getInputStream()));
    }

    public String generateFileName(Integer id, MultipartFile image) {
        return id + "/" + System.currentTimeMillis() + "_" + image.getOriginalFilename();
    }
}
