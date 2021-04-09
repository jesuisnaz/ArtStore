package com.nklymok.artstore.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import io.netty.handler.codec.base64.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
        File file = multipartFileToFile(multipartFile);
        putFileIntoS3(category, file);

    }

    private File multipartFileToFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try (FileOutputStream outputStream = new FileOutputStream(file)){
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private File s3ObjectToFile(S3Object object) {
        File file = new File(object.getKey());
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(object.getObjectContent().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void putFileIntoS3(String filepath, File file) {
        amazonS3.putObject(
                new PutObjectRequest(bucketName, String.format("%s/%s", filepath, file.getName()),
                        file).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private List<String> getObjectKeys() {
        ObjectListing listing = amazonS3.listObjects(bucketName, "featured/");
        List<S3ObjectSummary> summaries = listing.getObjectSummaries();
        List<String> keys = new ArrayList<>();
        while (listing.isTruncated()) {
            listing = amazonS3.listNextBatchOfObjects(listing);
            summaries.addAll(listing.getObjectSummaries());
        }
        for (S3ObjectSummary summary : summaries) {
            keys.add(summary.getKey());
        }
        return keys;
    }

    public List<String> getAllFeaturedAsBase64() {
        List<String> objectKeys = getObjectKeys();
        List<S3Object> objects = new ArrayList<>();
        List<String> featured = new ArrayList<>();

        for (String key : objectKeys) {
            objects.add(amazonS3.getObject(bucketName, key));
        }
        try {
            for (S3Object obj : objects) {
                featured.add(Base64.getEncoder().encodeToString(obj.getObjectContent().readAllBytes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return featured;
    }
}
