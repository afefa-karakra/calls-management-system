package com.example.convoconvert.Service.Interface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UploadServiceInterface {
    public ResponseEntity<String> handleFileUpload(MultipartFile file);
}
