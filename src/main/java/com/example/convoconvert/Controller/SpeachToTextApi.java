package com.example.convoconvert.Controller;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.example.convoconvert.Service.SpeachToTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
@RestController
@RequestMapping("/Api")
public class SpeachToTextApi {


//    RestTemplate restTemplate = new RestTemplate();
//
//    private static final String url = "";
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
//
//    @Bean
//    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
//        return args -> {
//
//
//        };
//
//    }
    @Autowired
    private SpeachToTextService speachToTextService;

    @PostMapping("/text")
    public ResponseEntity<String> transcribeAudio(@RequestParam File file) {
        try {
            // Save the uploaded file to a temporary location
//            File tempFile = File.createTempFile("audio", ".wav");
//            file.transferTo(tempFile);

            // Transcribe the audio file
            String transcript = speachToTextService.transcribe(file);

            // Delete the temporary file
            file.delete();

            return new ResponseEntity<>(transcript, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to transcribe audio file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
