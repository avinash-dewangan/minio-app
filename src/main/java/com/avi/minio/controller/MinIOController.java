package com.avi.minio.controller;


import com.avi.minio.modal.FileInfo;
import com.avi.minio.service.MinIOServiceS;
import io.minio.Result;
import io.minio.messages.Item;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/minio")
public class MinIOController {

    @Autowired
    private MinIOServiceS minioService;

    @PostMapping("/create-bucket/{bucketName}")
    public ResponseEntity<String> createBucket(@PathVariable String bucketName) {
        minioService.createBucket(bucketName);
        return ResponseEntity.ok("Bucket created successfully");
    }

    @PostMapping("/upload/{bucketName}")
    public ResponseEntity<String> uploadFile(@PathVariable String bucketName, @RequestParam("file") MultipartFile file) {
        try {
            minioService.uploadFile(bucketName, file.getOriginalFilename(), file.getInputStream(), file.getSize(), file.getContentType());
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File upload failed");
        }
    }
    @PostMapping("/create-folder/{bucketName}/{folderName}")
    public ResponseEntity<String> createFolder(@PathVariable String bucketName, @PathVariable String folderName) {
        try {
            minioService.createFolder(bucketName, folderName);
            return ResponseEntity.ok("Folder created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Folder creation failed: " + e.getMessage());
        }
    }

    @PostMapping("/create-folder")
    public ResponseEntity<String> createFolderNested(@RequestParam String bucketName, @RequestParam String folderName) {
        try {
            minioService.createFolder(bucketName, folderName);
            return ResponseEntity.ok("Folder created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Folder creation failed: " + e.getMessage());
        }
    }


    @PostMapping("/upload/{bucketName}/{folderName}")
    public ResponseEntity<String> uploadFile(@PathVariable String bucketName, @PathVariable String folderName, @RequestParam("file") MultipartFile file) {
        try {
            String objectName = folderName + "/" + file.getOriginalFilename();
            minioService.uploadFile(bucketName, objectName, file.getInputStream(), file.getSize(), file.getContentType());
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }

    //TODO  call from wpcwalyer
    @PostMapping("/uploads/{bucketName}")
    public ResponseEntity<String> uploadFileWithJson(@PathVariable String bucketName,
                                                     @RequestParam String folderName,
                                                     @RequestParam("file") MultipartFile file) {
        try {
            String objectName = folderName;
            minioService.uploadFile(bucketName, objectName, file.getInputStream(), file.getSize(), file.getContentType());
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }

    // base 64 get image format inside folder
    @GetMapping("/getDocumentFolder/{bucketName}/{folderName}/{objectName}")
    public ResponseEntity<String> getDocumentFolder(@PathVariable String bucketName,@PathVariable String folderName ,@PathVariable String objectName) throws IOException {

        String objectName1 = folderName + "/" + objectName;
        InputStream fileStream = minioService.downloadFile(bucketName, objectName1);
        byte[] imgData = null;
        String base64Image= null;
        imgData = IOUtils.toByteArray(fileStream);
        base64Image = Base64.encodeBase64String(imgData);
        if (fileStream != null) {
            return ResponseEntity.ok(base64Image);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }



    // getting image in byte format
    @GetMapping("/download/{bucketName}/{objectName}")
    public ResponseEntity<InputStream> downloadFile(@PathVariable String bucketName, @PathVariable String objectName) {
        InputStream fileStream = minioService.downloadFile(bucketName, objectName);
        if (fileStream != null) {
            return ResponseEntity.ok(fileStream);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // getting image in byte format //TODO TODO  call from wpcwalyer
    @PostMapping("/getDocument")
    public ResponseEntity<InputStream> downloadFiles(@RequestParam String bucketName, @RequestParam String objectName) {
        InputStream fileStream = minioService.downloadFile(bucketName, objectName);
        if (fileStream != null) {
            return ResponseEntity.ok(fileStream);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }



    // base 64 get image format
    @GetMapping("/getDocument/{bucketName}/{objectName}")
    public ResponseEntity<String> getDocument(@PathVariable String bucketName, @PathVariable String objectName) throws IOException {
        InputStream fileStream = minioService.downloadFile(bucketName, objectName);
        byte[] imgData = null;
        String base64Image= null;
        imgData = IOUtils.toByteArray(fileStream);
        base64Image = Base64.encodeBase64String(imgData);
        if (fileStream != null) {
            return ResponseEntity.ok(base64Image);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/listObj/{bucketName}")
    public ResponseEntity<Iterable<Result<Item>>> listFilesObj(@PathVariable String bucketName) {
        Iterable<Result<Item>> files = minioService.listFiles(bucketName);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/list/{bucketName}")
    public ResponseEntity<List<FileInfo>> listFiles(@PathVariable String bucketName) {
        Iterable<Result<Item>> results = minioService.listFiles(bucketName);
        List<FileInfo> files = new ArrayList<>();

        for (Result<Item> result : results) {
            try {
                Item item = result.get();
                FileInfo fileInfo = new FileInfo(item.objectName(), item.size(), item.lastModified());
                files.add(fileInfo);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).build();
            }
        }
        return ResponseEntity.ok(files);
    }




}
