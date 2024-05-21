package com.example.convoconvert.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
@Service
public class SpeachToTextService{

    @Autowired
    private RestTemplate restTemplate;

//    public String createTranscriptionJob(String filePath) {
//        try {
//            String url = "https://asr.api.speechmatics.com/v2/jobs/";
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//            headers.set("Authorization", "Bearer 2ffRxB9aEbHd5LFmIRZwiH0iuGNoRcpQ");
//
//            String config = "{\"type\": \"transcription\",\"transcription_config\": { \"operating_point\":\"enhanced\", \"language\": \"en\", \"enable_entities\": true}}";
//
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            body.add("config", config);
//            body.add("file", new FileSystemResource(new File(filePath)));
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
//
//            return response.getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "{\"error\": \"An error occurred while processing the transcription job\"}";
//        }
//    }

    public void makePostRequest() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer 2ffRxB9aEbHd5LFmIRZwiH0iuGNoRcpQ");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("config", "{\"type\": \"transcription\",\"transcription_config\": { \"operating_point\":\"enhanced\", \"language\": \"en\", \"enable_entities\": true}}");

        // Make sure the path points to the correct location of your file
        Resource fileResource = new FileSystemResource("C:\\Users\\Lenovo\\Downloads\\example.wav");
        body.add("data_file", fileResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://asr.api.speechmatics.com/v2/jobs/",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        System.out.println("Response: " + response.getBody());
    }

}
