package com.example.convoconvert.Service;

import com.example.convoconvert.Entity.Calls;
import com.example.convoconvert.Entity.Customer;
import com.example.convoconvert.Entity.Employee;
import com.example.convoconvert.Repository.CallsInterfaceRepository;
import com.example.convoconvert.Repository.CustomerInterfaceRepository;
import com.example.convoconvert.Repository.EmployeeInterfaceRepository;
import com.example.convoconvert.Service.Interface.UploadServiceInterface;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.*;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.PostConstruct;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UploadService implements UploadServiceInterface {

    @Autowired
    private CallsInterfaceRepository callsInterfaceRepository;

    @Autowired
    private CustomerInterfaceRepository customerInterfaceRepository;

    @Autowired
    private EmployeeInterfaceRepository employeeInterfaceRepository;

    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${google.credentials.file}")
    private Resource googleCredentialsResource;

    @Value("${file.chunk-size}")
    private int chunkSize;

    private SpeechClient speechClient;

    private final RestTemplate restTemplate;

    @Autowired
    public UploadService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void init() throws IOException {
        createDirectories();
        initializeSpeechClient();
    }

    private void createDirectories() throws IOException {
        Path path = Paths.get(uploadDir).toAbsolutePath();
        Files.createDirectories(path);
        uploadDir = path.toString();
        logger.info("Upload directory initialized at: {}", uploadDir);
    }

    private void initializeSpeechClient() throws IOException {
        if (!googleCredentialsResource.exists()) {
            logger.error("Credentials file not found at {}", googleCredentialsResource.getDescription());
            throw new IllegalStateException("Credentials file not found");
        }
        try (InputStream credentialsStream = googleCredentialsResource.getInputStream()) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            speechClient = SpeechClient.create(SpeechSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build());
            logger.info("Google Speech Client has been initialized.");
        } catch (IOException e) {
            logger.error("Failed to initialize Google Speech Client", e);
            throw new IllegalStateException("Failed to initialize Google Speech Client", e);
        }
    }

    public ResponseEntity<String> handleFileUpload(MultipartFile file, String customerName, Integer customerNumber, String employeeName , String keywords, boolean started , String status,String date) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.toLowerCase().endsWith(".wav")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only WAV audio files are allowed.");
        }

        String newFileName = UUID.randomUUID() + ".wav";
        Path targetPath = Paths.get(uploadDir, newFileName);
        Calls call=new Calls();
        call.setAudio(newFileName);
        try {
            file.transferTo(targetPath);
            String audioText = transcribeAudio(targetPath);
            call.setAudioText(audioText);
            String nerText=wojood(audioText);
            call.setNerText(nerText);
            List<String> nerTag=getNerTagList(nerText);
            call.setNerTags(nerTag);
            Customer customer= customerInterfaceRepository.findByName(customerName);
            Employee employee= employeeInterfaceRepository.findByName(employeeName);
            call.setCustomer(customer);
            call.setEmployee(employee);
            call.setKeywords(keywords);
            call.setStarted(started);
            call.setStatus(status);
            call.setDate(String.valueOf(date));
            callsInterfaceRepository.save(call);
            return ResponseEntity.ok("File uploaded successfully: " + targetPath + "\nTranscription: " + audioText);
        } catch (IOException | InterruptedException e) {
            logger.error("Failed to upload file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private String transcribeAudio(Path filePath) throws IOException, InterruptedException, ExecutionException {
        byte[] data = Files.readAllBytes(filePath);
        AudioFormat format = getAudioFormat(filePath);
        int sampleRate = (int) format.getSampleRate();

        if (data.length > chunkSize) { // Use configured chunk size
            return transcribeAudioInChunks(data, sampleRate);

        } else {
            return transcribeAudioLongRunning(data, sampleRate);
        }
    }

    private String transcribeAudioLongRunning(byte[] audioData, int sampleRate) throws IOException, InterruptedException, ExecutionException {
        ByteString audioBytes = ByteString.copyFrom(audioData);

        RecognitionConfig config = RecognitionConfig.newBuilder()
                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                .setSampleRateHertz(sampleRate)
                .setLanguageCode("ar-SA") // Set Arabic locale
                .setUseEnhanced(true) // Use enhanced model
                .build();
        RecognitionAudio audio = RecognitionAudio.newBuilder()
                .setContent(audioBytes)
                .build();

        OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response =
                speechClient.longRunningRecognizeAsync(config, audio);

        // Wait for the operation to complete
        LongRunningRecognizeResponse longRunningRecognizeResponse = response.get();

        StringBuilder transcription = new StringBuilder();
        for (SpeechRecognitionResult result : longRunningRecognizeResponse.getResultsList()) {
            transcription.append(result.getAlternativesList().get(0).getTranscript()).append(" ");
        }

        return transcription.toString();
    }

    private String transcribeAudioInChunks(byte[] audioData, int sampleRate) throws IOException, InterruptedException, ExecutionException {
        int adjustedChunkSize = chunkSize - 100000; // Slightly less than the configured chunk size to be safe
        int numChunks = (int) Math.ceil((double) audioData.length / adjustedChunkSize);
        StringBuilder fullTranscription = new StringBuilder();

        for (int i = 0; i < numChunks; i++) {
            int start = i * adjustedChunkSize;
            int end = Math.min(audioData.length, (i + 1) * adjustedChunkSize);
            byte[] chunk = new byte[end - start];
            System.arraycopy(audioData, start, chunk, 0, chunk.length);
            String chunkTranscription = transcribeAudioLongRunning(chunk, sampleRate);
            fullTranscription.append(chunkTranscription).append(" ");
        }

        return fullTranscription.toString();
    }

    private AudioFormat getAudioFormat(Path filePath) throws IOException {
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(filePath.toFile())) {
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(filePath.toFile());
            return fileFormat.getFormat();
        } catch (Exception e) {
            throw new IOException("Failed to get audio format", e);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception exc) {
        logger.error("Error handling request: {}", exc.getMessage(), exc);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + exc.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File too large!");
    }

    private String wojood(String text) {
        HttpHeaders wojoodHeaders = new HttpHeaders();
        wojoodHeaders.set("User-Agent", "Mozilla/5.0");
        wojoodHeaders.set("Content-Type", "application/json");
        String WojoodBody = String.format("{ \"sentence\": \"%s\", \"mode\": \"3\" }", text);
        HttpEntity<String> wojoodRequestEntity = new HttpEntity<>(WojoodBody, wojoodHeaders);
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "https://ontology.birzeit.edu/sina/v2/api/wojood/?apikey=BZUstudents",
                    HttpMethod.POST,
                    wojoodRequestEntity,
                    String.class
            );
            String responseBody = responseEntity.getBody();
            System.out.println("Response Body: " + responseBody);
            if (responseBody != null) {
                JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
                JsonNode respNode = jsonNode.get("resp");
                if (respNode != null && respNode.isArray()) {
                    StringBuilder respText = new StringBuilder();
                    Iterator<JsonNode> elements = respNode.elements();
                    while (elements.hasNext()) {
                        JsonNode element = elements.next();
                        respText.append(element.asText());
                        if (elements.hasNext()) {
                            respText.append(" ");
                        }
                    }
                    return respText.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WojoodBody;
    }
    private List<String> getNerTagList(String text) {
        Pattern pattern = Pattern.compile("(<span.*?>.*?</span>)");
        Matcher matcher = pattern.matcher(text);
        List<String> nerTags = new ArrayList<>();

        while (matcher.find()) {
            nerTags.add(matcher.group(1));
        }
        return nerTags;
    }

}
