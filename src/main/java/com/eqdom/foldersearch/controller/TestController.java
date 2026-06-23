package com.eqdom.foldersearch.controller;

import com.eqdom.foldersearch.service.MinioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final MinioService minioService;

    public TestController(MinioService minioService) {
        this.minioService = minioService;
    }

    @PostMapping("/test-minio")
    public void test() throws Exception {
        minioService.testUpload();
    }
}
