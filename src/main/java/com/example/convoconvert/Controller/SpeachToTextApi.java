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
@RequestMapping("/Api")
public class SpeachToTextApi {

    @Autowired
    private SpeachToTextService speachToTextService;

    @PostMapping("/text")
    public ResponseEntity<String> transcribeAudio(@RequestParam("file") MultipartFile file) {
        try {
            // Convert MultipartFile to File
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);

            // Make the Unirest request
            HttpResponse<String> response = Unirest.post("https://asr.api.speechmatics.com/v2/jobs/")
                    .header("Authorization", "Bearer 2ffRxB9aEbHd5LFmIRZwiH0iuGNoRcpQ")
                    .field("config", "{\"type\": \"transcription\",\"transcription_config\": { \"operating_point\":\"enhanced\", \"language\": \"en\", \"enable_entities\": true}}")
                    .field("file", convFile)
                    .asString();

            // Return the response
            return ResponseEntity.ok(response.getBody());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("File processing error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
