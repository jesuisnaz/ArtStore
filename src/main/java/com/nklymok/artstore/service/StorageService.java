package com.nklymok.artstore.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StorageService {

    void uploadFile(MultipartFile multipartFile, String category);

    String getAsBase64(String key) throws IOException;

    List<String> getAllAsBase64(String path);

}
