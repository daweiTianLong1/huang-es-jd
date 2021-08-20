package com.huang.controller;

import com.huang.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//请求编写
@RestController//只返回数据
public class ContentController {

    @Autowired
    private ContentService contentService;

//    @GetMapping("/parse/{keyword}")
//    public Boolean parse(@PathVariable("keyword") String keyword) throws Exception {
//        return contentService.parseContent(keyword);
//    }


    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String, Object>> search(@PathVariable("keyword") String keyword,
                                            @PathVariable("pageNo") int pageNo,
                                            @PathVariable("pageSize") int pageSize) throws IOException {

        System.out.println(contentService.searchPageHighlightBuilder(keyword));
        return contentService.searchPageHighlightBuilder(keyword);
    }










}




