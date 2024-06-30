package com.example.convoconvert.Service;

import com.example.convoconvert.Entity.Calls;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SpeachToTextService {

    private final RestTemplate restTemplate;

    @Autowired
    public SpeachToTextService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> convertSpeechToText(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer UGQ5b3MLYxEE5aue9xZ7OUTMiVR8Athq");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("config", "{\"type\": \"transcription\",\"transcription_config\": { \"operating_point\":\"enhanced\", \"language\": \"ar\", \"enable_entities\": true}}");
        body.add("data_file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseID= restTemplate.exchange(
                "https://asr.api.speechmatics.com/v2/jobs/",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        String responseBody = responseID.getBody();
//        System.out.println(responseBody);
        JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
        String jobid = jsonNode.get("id").asText();
//        System.out.println("id: "+jobid);
        HttpHeaders transcriptHeaders = new HttpHeaders();
        transcriptHeaders.set("X-SM-EAR-Tag", "<string>");
        transcriptHeaders.set("Authorization", "Bearer UGQ5b3MLYxEE5aue9xZ7OUTMiVR8Athq");
        transcriptHeaders.set("Accept", "application/json");
        HttpEntity<String> transcriptRequestEntity = new HttpEntity<>(transcriptHeaders);
        ResponseEntity<String> jobDetails;
        String jobStatus="running";
        while (jobStatus.equals("running")){
            jobDetails= restTemplate.exchange(
                    "https://asr.api.speechmatics.com/v2/jobs/"+jobid,
                    HttpMethod.GET,
                    transcriptRequestEntity,
                    String.class
            );
            JsonNode jsonNode2 = new ObjectMapper().readTree(jobDetails.getBody());
            jobStatus = jsonNode2.get("job").get("status").asText();
            System.out.println("status: "+jobStatus);
        }
//        System.out.println("https://asr.api.speechmatics.com/v2/jobs/"+jobid+"/transcript?format=txt");
        ResponseEntity<String> responseText =restTemplate.exchange(
                "https://asr.api.speechmatics.com/v2/jobs/"+jobid+"/transcript?format=txt",
                HttpMethod.GET,
                transcriptRequestEntity,
                String.class
        );
        String text=responseText.getBody();
        System.out.println("text: "+text);
        HttpHeaders wojoodHeaders = new HttpHeaders();
        wojoodHeaders.set("User-Agent","Mozilla/5.0");
        wojoodHeaders.set("Content-Type", "application/json");
        String WojoodBody=String.format("{ \"sentence\": \"%s\", \"mode\": \"3\" }", text);
        HttpEntity<String> wojoodRequestEntity = new HttpEntity<>(WojoodBody, wojoodHeaders);
        return restTemplate.exchange(
                "https://ontology.birzeit.edu/sina/v2/api/wojood/?apikey=BZUstudents",
                HttpMethod.POST,
                wojoodRequestEntity,
                String.class
        );
    }

    public ResponseEntity<String> convertSpeechToText(String text) throws IOException {

        HttpHeaders wojoodHeaders = new HttpHeaders();
        wojoodHeaders.set("User-Agent","Mozilla/5.0");
        wojoodHeaders.set("Content-Type", "application/json");
        String WojoodBody=String.format("{ \"sentence\": \"%s\", \"mode\": \"3\" }", text);
        HttpEntity<String> wojoodRequestEntity = new HttpEntity<>(WojoodBody, wojoodHeaders);
        return restTemplate.exchange(
                "https://ontology.birzeit.edu/sina/v2/api/wojood/?apikey=BZUstudents",
                HttpMethod.POST,
                wojoodRequestEntity,
                String.class
        );
    }
}


