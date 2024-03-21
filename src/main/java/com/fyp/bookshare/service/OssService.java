package com.fyp.bookshare.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public interface OssService {

    public void uploadImage(MultipartFile image, String fileName ) throws IOException;
}
