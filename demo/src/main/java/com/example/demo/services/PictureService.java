package com.example.demo.services;

import com.example.demo.models.PictureResponse;
import com.example.demo.models.PictureUploadSend;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PictureService {
    // 插入Picture，第一个参数是Model，第2个参数是图像文件
    String insertOnePicture(PictureUploadSend model, MultipartFile file);
    // 获取所有图片(根据数据库时间递减)
    List<PictureResponse> getAllPictures(int page, int sum, int type);
}
