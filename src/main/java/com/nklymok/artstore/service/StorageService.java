package com.nklymok.artstore.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class StorageService {

    private final AmazonS3 amazonS3;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Autowired
    public StorageService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void uploadFile(MultipartFile multipartFile, String category) {
         File file = convertMultipartFileToFile(multipartFile);
         putFileIntoS3(category, file);

    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(multipartFile.getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void putFileIntoS3(String filepath, File file) {
        amazonS3.putObject(
                new PutObjectRequest(bucketName, String.format("%s/%s", filepath, file.getName()),
                        file).withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
