package com.example.convoconvert.Controller;

import com.example.convoconvert.Service.SpeachToTextService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Speach To Text")
@RestController
public class SpeachToTextApi {
   final private SpeachToTextService speachToTextService;

    public SpeachToTextApi(SpeachToTextService speachToTextService) {
        this.speachToTextService = speachToTextService;
    }

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
