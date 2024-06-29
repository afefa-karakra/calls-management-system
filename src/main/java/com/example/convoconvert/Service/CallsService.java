package com.example.convoconvert.Service;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.Entity.Calls;
import com.example.convoconvert.Entity.Customer;
import com.example.convoconvert.Exception.ResourceNotFoundException;
import com.example.convoconvert.Repository.CallsInterfaceRepository;
import com.example.convoconvert.Repository.CustomerInterfaceRepository;
import com.example.convoconvert.Service.Interface.CallsServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CallsService implements CallsServiceInterface {


    private CallsInterfaceRepository callsInterfaceRepository;
    private CustomerInterfaceRepository customerInterfaceRepository;

    public CallsService(CallsInterfaceRepository callsInterfaceRepository , CustomerInterfaceRepository customerInterfaceRepository) {
        this.callsInterfaceRepository = callsInterfaceRepository;
        this.customerInterfaceRepository = customerInterfaceRepository;
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

        calls.setAudioText(String.valueOf(callsDTO.getAudioText()));
        calls.setDate(callsDTO.getDate());

        Calls updateCalls = callsInterfaceRepository.save(calls);
        return mapToDTO(updateCalls);
    }

//    @Override
//    public CallsDTO updateFieldsOfCall(long id, Map<String, Optional> map) {
//
//        Calls calls = callsInterfaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Calls", "id", id));
//
//        for (Map.Entry<String , Optional> hm : map.entrySet()){
//            String keyFromMap = hm.getKey();
//
//            if(keyFromMap.equals("audioText")){
//                String obj = hm.getValue().toString();
//
//                calls.setAudioText();
//            }
//        }
//        Calls updateCalls = callsInterfaceRepository.save(calls);
//        return mapToDTO(updateCalls);
//    }


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
        callsDTO.setTime(calls.getTime());
        callsDTO.setStarted(calls.isStarted());
        callsDTO.setCustomerName(calls.getCustomer().getName());
        callsDTO.setEmployeeName(calls.getEmployee().getName());
        callsDTO.setPhoneNumber(calls.getCustomer().getPhoneNumber());
        callsDTO.setSecondPhoneNumber(calls.getCustomer().getSecondPhoneNumber());

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
        calls.setTime(callsDTO.getTime());
        calls.setId(callsDTO.getId());
        calls.setTrash(callsDTO.isTrash());
        calls.setStarted(callsDTO.isStarted());

        if (callsDTO.getCustomerName() != null) {
            Customer customer = customerInterfaceRepository.findById(callsDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", callsDTO.getId()));
            calls.setCustomer(customer);
        }

        return calls;
    }
}