package com.huang.controller;

import com.huang.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class FileController {


    @Autowired
    private FileService fileService;

    @RequestMapping("/upload")
    public String httpUpload(@RequestParam("files") MultipartFile files[]){

        System.out.println(files);
        return fileService.uploadFile(files);

    }
}