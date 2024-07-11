package com.example.convoconvert.Service.Interface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public interface UploadServiceInterface {
    public ResponseEntity<String> handleFileUpload(MultipartFile file, String customerName, Integer customerNumber, String employeeName , String keywords, boolean started , String status,String date);
}
