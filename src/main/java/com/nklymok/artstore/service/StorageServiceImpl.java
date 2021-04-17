package com.nklymok.artstore.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
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
public class StorageServiceImpl implements StorageService {

    private final AmazonS3 amazonS3;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Autowired
    public StorageServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void uploadFile(MultipartFile multipartFile, String category) {
        File file = multipartFileToFile(multipartFile);
        putFileIntoS3(category, file);

    }

    public String getAsBase64(String key) throws IOException {
        System.out.println("Passed key: " + key);
        return Base64.getEncoder().encodeToString(amazonS3.getObject(bucketName, key).getObjectContent().readAllBytes());
    }

    public List<String> getAllAsBase64(String path) {
        List<String> objectKeys = getObjectKeys(path);
        List<S3Object> objects = new ArrayList<>();
        List<String> b64Values = new ArrayList<>();

        for (String key : objectKeys) {
            objects.add(amazonS3.getObject(bucketName, key));
        }
        try {
            for (S3Object obj : objects) {
                b64Values.add(Base64.getEncoder().encodeToString(obj.getObjectContent().readAllBytes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b64Values;
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

    private void putFileIntoS3(String filepath, File file) {
        amazonS3.putObject(
                new PutObjectRequest(bucketName, String.format("%s/%s", filepath, file.getName()),
                        file).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private List<String> getObjectKeys(String path) {
        ObjectListing listing = amazonS3.listObjects(bucketName, path+"/");
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

}
