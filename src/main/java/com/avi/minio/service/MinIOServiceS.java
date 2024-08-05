package com.avi.minio.service;


import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class MinIOServiceS {

    @Autowired
    private MinioClient minioClient;

    public void createBucket(String bucketName) {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void uploadFile(String bucketName, String objectName, InputStream inputStream, long size, String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, size, -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void upload(Path source, InputStream file, Map<String, String> headers, String bucketName) throws
            MinioException {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .headers(headers)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e.getMessage());
        }
    }

    public void upload(Path source, InputStream file, String bucketName) throws
            MinioException {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            throw new MinioException("Error while fetching files in Minio", e.getMessage());
        }
    }

    public InputStream downloadFile(String bucketName, String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Iterable<Result<Item>> listFiles(String bucketName) {
        return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
    }
}

