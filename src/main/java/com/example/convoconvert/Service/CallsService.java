package com.example.convoconvert.Service;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.Entity.Calls;
import com.example.convoconvert.Exception.ResourceNotFoundException;
import com.example.convoconvert.Repository.CallsInterfaceRepository;
import com.example.convoconvert.Service.Interface.CallsServiceInterface;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CallsService implements CallsServiceInterface {

    @Autowired
    private CallsInterfaceRepository callsInterfaceRepository;


    private RestTemplate restTemplate;
    @Autowired
    public CallsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CallsDTO getCallById(long id) {

        Calls calls = callsInterfaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Calls", "id", id));
        return mapToDTO(calls);
    }

    @Override
    public List<CallsDTO> getAllCalls() {

        List<Calls> callsList = callsInterfaceRepository.findAll();
        return callsList.stream().map(calls -> mapToDTO(calls))
                .collect(Collectors.toList());
    }

    @Override
    public CallsDTO createCall(CallsDTO callsDTO) {
        // convert DTO to entity
        Calls calls = mapToEntity(callsDTO);
        Calls newCall = callsInterfaceRepository.save(calls);

        // convert entity to DTO
        CallsDTO callResponse = mapToDTO(newCall);
        return callResponse;
    }

    @Override
    public CallsDTO updateCall(CallsDTO callsDTO, long id) {
        Calls calls = callsInterfaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Calls", "id", id));

        calls.setAudioText(callsDTO.getAudioText());
        calls.setDate(callsDTO.getDate());

        Calls updateCalls = callsInterfaceRepository.save(calls);
        return mapToDTO(updateCalls);
    }

    @Override
    public CallsDTO updateFieldsOfCall(long id, Map<String, Optional> map) {

        Calls calls = callsInterfaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Calls", "id", id));

        for (Map.Entry<String , Optional> hm : map.entrySet()){
            String keyFromMap = hm.getKey();

            if(keyFromMap.equals("audioText")){
                String obj = hm.getValue().toString();

                calls.setAudioText(obj);
            }
        }
        Calls updateCalls = callsInterfaceRepository.save(calls);
        return mapToDTO(updateCalls);
    }


    @Override
    public void deleteCallById(long id) {

        Calls calls = callsInterfaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Calls", "id", id));
        callsInterfaceRepository.delete(calls);

    }

    public List<CallsDTO> getStartedCalls(){
        List<Calls> startedCalls = callsInterfaceRepository.getStartedCalls();
        return startedCalls.stream().map(calls -> mapToDTO(calls))
                .collect(Collectors.toList());
    }

    public List<CallsDTO> getSolveCalls(){
        List<Calls> solveCalls = callsInterfaceRepository.getSolveCalls();
        return solveCalls.stream().map(calls -> mapToDTO(calls))
                .collect(Collectors.toList());
    }

    public List<CallsDTO> getNotSolveCalls(){
        List<Calls> notSolveCalls = callsInterfaceRepository.getNotSolveCalls();
        return notSolveCalls.stream().map(calls -> mapToDTO(calls))
                .collect(Collectors.toList());
    }

    @Override
    public List<CallsDTO> getListOfCallsFillter(long id,  Date date) {
        List<Calls> callsList = callsInterfaceRepository.getListOfCallsFilter(id , date);
        return callsList.stream().map(calls -> mapToDTO(calls)).collect(Collectors.toList());

    }


    public List<CallsDTO> getKeywordsByCallId(long id) {
        List<String> keywords = callsInterfaceRepository.getKeywordsByCallId(id);
        return keywords.stream()
                .map(calls -> new CallsDTO(calls))
                .collect(Collectors.toList());
    }

    @Override
    public List<CallsDTO> getnerTagsByCallId(long id) {
        List<String> nerTags = callsInterfaceRepository.getnerTagsByCallId(id);
        return nerTags.stream()
                .map(calls -> new CallsDTO(calls)).collect(Collectors.toList());
    }

    @Override
    public List<CallsDTO> getEntityClassesByCallId(long id) {
        List<String> entityClasses = callsInterfaceRepository.getEntityClassesByCallId(id);
        return entityClasses.stream()
                .map(calls -> new CallsDTO(calls)).collect(Collectors.toList());
    }

    @Override
    public List<CallsDTO> getDateByCallId(long id) {
        List<String> date= callsInterfaceRepository.getDateByCallId(id);
        return date.stream()
                .map(calls -> new CallsDTO(calls)).collect(Collectors.toList());
    }

    @Override
    public void addCall(MultipartFile file) throws IOException {
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
//            System.out.println("status: "+jobStatus);
        }
//        System.out.println("https://asr.api.speechmatics.com/v2/jobs/"+jobid+"/transcript?format=txt");
        ResponseEntity<String> responseText =restTemplate.exchange(
                "https://asr.api.speechmatics.com/v2/jobs/"+jobid+"/transcript?format=txt",
                HttpMethod.GET,
                transcriptRequestEntity,
                String.class
        );
        String text=responseText.getBody();
//        System.out.println("text: "+text);
        HttpHeaders wojoodHeaders = new HttpHeaders();
        wojoodHeaders.set("User-Agent","Mozilla/5.0");
        wojoodHeaders.set("Content-Type", "application/json");
        String WojoodBody=String.format("{ \"sentence\": \"%s\", \"mode\": \"1\" }", text);
        HttpEntity<String> wojoodRequestEntity = new HttpEntity<>(WojoodBody, wojoodHeaders);
        HttpEntity<String> WojoodText =restTemplate.exchange(
                "https://ontology.birzeit.edu/sina/v2/api/wojood/?apikey=BZUstudents",
                HttpMethod.POST,
                wojoodRequestEntity,
                String.class
        );
        Calls call=new Calls();
        call.setAudioText(responseText.getBody());
        System.out.println(call.getAudioText());

        call.setNerTags(WojoodText.getBody());
        System.out.println(call.getNerTags());
        callsInterfaceRepository.save(call);
    }


//    @Override
//    public List<CallsDTO> geDataWithEmployeeNameAndCustomerId() {
//
//        List<String> data = callsInterfaceRepository.geDataWithEmployeeNameAndCustomerId();
//        return data.stream()
//                .map(calls -> new CallsDTO(calls)).collect(Collectors.toList());
//    }

    private CallsDTO mapToDTO(Calls calls) {
        CallsDTO callsDTO = new CallsDTO();
        callsDTO.setId(calls.getId());
        callsDTO.setAudioText(calls.getAudioText());
        callsDTO.setDate(calls.getDate());
        callsDTO.setKeywords(calls.getKeywords());
        callsDTO.setStatus(calls.getStatus());
        callsDTO.setEntityClasses(calls.getEntityClasses());
        callsDTO.setNerTags(calls.getNerTags());
        callsDTO.setTrash(calls.isTrash());
        callsDTO.setStarted(calls.isStarted());
        callsDTO.setCustomerName(calls.getCustomer().getName());
        callsDTO.setEmployeeName(calls.getEmployee().getName());
        return callsDTO;
    }

    private Calls mapToEntity(CallsDTO callsDTO) {
        Calls calls = new Calls();
        calls.setAudioText(callsDTO.getAudioText());
        calls.setDate(callsDTO.getDate());
        calls.setKeywords(callsDTO.getKeywords());
        calls.setStatus(callsDTO.getStatus());
        calls.setEntityClasses(callsDTO.getEntityClasses());
        calls.setNerTags(callsDTO.getNerTags());
        calls.setId(callsDTO.getId());
        calls.setTrash(callsDTO.isTrash());
        calls.setStarted(callsDTO.isStarted());

        return calls;
    }
}