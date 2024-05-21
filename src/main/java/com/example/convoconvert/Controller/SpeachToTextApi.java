package com.example.convoconvert.Controller;

import com.example.convoconvert.Service.SpeachToTextService;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
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
//    @GetMapping("/transcribe")
//    public String transcribe(@RequestParam String filePath) {
//        return speachToTextService.createTranscriptionJob(filePath);
//    }
    @PostMapping("transcribe")
    public void makePostRequest(){
        speachToTextService.makePostRequest();
    }
}
