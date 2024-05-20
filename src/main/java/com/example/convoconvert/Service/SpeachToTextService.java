package com.example.convoconvert.Service;

import com.example.convoconvert.Service.Interface.SpeachToTextInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;

@Service
public class SpeachToTextService  {

//    @Value("${speechmatics.api.url}")
//    private String apiUrl;
//
//    @Value("${speechmatics.api.token}")
//    private String URL2;


    private String apiKey="P54CdayyiQYCFZ6NkGCFV6doXtk4v6pe";
    private static final String URL = "https://api.speechmatics.com/v2/jobs/";

    private final RestTemplate restTemplate;

    public SpeachToTextService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    public String transcribe(File audioFile) throws IOException {
//
////        HttpResponse<String> response = Unirest.post("https://asr.api.speechmatics.com/v2/jobs/")
////                .header("Authorization", "Bearer 2ffRxB9aEbHd5LFmIRZwiH0iuGNoRcpQ")
////                .field("config", "{\"type\": \"transcription\",\"transcription_config\": { \"operating_point\":\"enhanced\", \"language\": \"en\", \"enable_entities\": true}}")
////                .field("file", new File("postman-cloud:///1ef153a2-a963-4bf0-b96b-6e9fed359fca"))
////                .asString();
//
//
//
//    }
}
