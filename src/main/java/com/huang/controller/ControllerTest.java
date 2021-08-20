//package com.huang.controller;
//
//import com.huang.service.FileService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * @author PanYi
// * @date 2021/08/19日 16:51
// */
//@Controller
//public class ControllerTest {
//
//
//    @Autowired
//    private FileService fileService;
//
//    @PostMapping("/file/post/{files}")
//    public String postUpload(@PathVariable("files") MultipartFile files[]) throws Exception {
//
//        return fileService.uploadFile(files);
//
//    }
//
//
//
//}
