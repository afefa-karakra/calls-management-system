package com.example.convoconvert.Controller;

import com.example.convoconvert.Service.SpeachToTextService;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class SpeachToTextApi {

    @Autowired
    private SpeachToTextService speachToTextService;

//    @CrossOrigin
//    @PostMapping("/transcribe")
//    public ResponseEntity<String> convertSpeechToText(@RequestParam("file") MultipartFile file) throws IOException {
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
//        }
//        return speachToTextService.convertSpeechToText(file);
//
//    }
}
