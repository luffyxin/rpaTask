package com.example.demo.controller;

import com.example.demo.biz.MinioBiz;
import com.example.demo.entity.DataFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@RestController
@RequestMapping("/minio")
public class MinioController {

    @Resource
    private MinioBiz minioBiz;


    @PostMapping("/upload")
    public DataFile upload(@RequestParam("file") MultipartFile file) {
        return minioBiz.uploadFile(file);
    }

}
