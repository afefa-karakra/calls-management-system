package com.example.convoconvert.Controller;

import com.example.convoconvert.Service.Interface.UploadServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    final private UploadServiceInterface uploadServiceInterface;

    public UploadController(UploadServiceInterface uploadServiceInterface) {
        this.uploadServiceInterface = uploadServiceInterface;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
       return uploadServiceInterface.handleFileUpload(file);
    }
}
