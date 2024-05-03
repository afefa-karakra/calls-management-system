package com.example.convoconvert.Service.Interface;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.DTO.EmployeeDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CallsServiceInterface {

    CallsDTO getCallById (long id);
    CallsDTO createCall (CallsDTO callsDTO);
    CallsDTO updateCall (CallsDTO callsDTO , long id);

    CallsDTO updateFieldsOfCall (long id , Map<String , Optional>  map);
    List<CallsDTO> getAllCalls();
    void deleteCallById (long id);
    List<CallsDTO> getStartedCalls();
    List<CallsDTO> getSolveCalls();
    List<CallsDTO> getNotSolveCalls();
}
