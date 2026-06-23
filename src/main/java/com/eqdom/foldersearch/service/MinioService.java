package com.eqdom.foldersearch.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class MinioService {
    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadFile(String key,byte[] fileContent) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(key)
                        .stream(new ByteArrayInputStream(fileContent), fileContent.length, -1)
                        .contentType("application/pdf")
                        .build()
        );

        return key;
    }

    public void testUpload() throws Exception{
        String text = "hello minio";
        uploadFile("test.txt", text.getBytes());
    }
}
