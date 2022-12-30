package com.leyou.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.Lyexception;
import com.leyou.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadProperties prop;

    // private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");
    public String uploadImage(MultipartFile file) {
        try {
            //校验文件类型
            String contentType = file.getContentType();
            if (!prop.getAllowTypes().contains(contentType)){
                throw new Lyexception(ExceptionEnum.INVALID_FILE_TYPE);
            }
            //校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image==null){
                throw new Lyexception(ExceptionEnum.INVALID_FILE_TYPE);
            }

            //上传到FastDFS
            // 1、获取文件后缀名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            //2、上传
            StorePath storePath = storageClient.uploadFile(
                    file.getInputStream(), file.getSize(), extension, null);
            //返回路径
            return prop.getBaseurl() + storePath.getFullPath();
        } catch (IOException e) {
            //上传失败
            log.error("[文件上传] 上传文件失败！", e);
            throw new Lyexception(ExceptionEnum.UPLOAD_FILE_ERROR);
        }

    }
}

