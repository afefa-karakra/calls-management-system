package com.example.convoconvert.Controller;

import com.example.convoconvert.Service.Interface.UploadServiceInterface;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
public class UploadController {

    final private UploadServiceInterface uploadServiceInterface;

    public UploadController(UploadServiceInterface uploadServiceInterface) {
        this.uploadServiceInterface = uploadServiceInterface;
    }

    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam String customerName,
            @RequestParam Integer customerNumber,
            @RequestParam String employeeName,
            @RequestParam String keywords,
            @RequestParam boolean started,
            @RequestParam String status,
            @RequestParam String date) {
        return uploadServiceInterface.handleFileUpload(file, customerName, customerNumber, employeeName, keywords, started, status,date);
    }
}
