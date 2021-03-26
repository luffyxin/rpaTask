package com.example.demo.biz;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.demo.config.MinioConfig;
import com.example.demo.constant.CommonConstant;
import com.example.demo.entity.DataFile;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
public class MinioBiz {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioConfig minioConfig;



    public DataFile uploadFile(MultipartFile file) {
        DataFile dataFile = new DataFile();
        try {
            // 上传到minio
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(CommonConstant.BUCKET).object(file.getOriginalFilename())
                            .stream(
                                    file.getInputStream(), file.getInputStream().available(), -1)
                            .build());
            dataFile.setObjectName(file.getOriginalFilename());
            dataFile.setBucketName(CommonConstant.BUCKET);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("上传失败");
        }
        return dataFile;
    }

}
