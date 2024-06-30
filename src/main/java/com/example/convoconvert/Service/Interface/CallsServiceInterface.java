package com.example.convoconvert.Service.Interface;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.DTO.EmployeeDTO;
import com.example.convoconvert.Entity.Calls;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CallsServiceInterface {

    CallsDTO getCallById (long id);
    CallsDTO createCall (CallsDTO callsDTO);
    CallsDTO updateCall (CallsDTO callsDTO , long id);

 //   CallsDTO updateFieldsOfCall (long id , Map<String , Optional>  map);
    List<CallsDTO> getAllCalls();
    void deleteCallById (long id);
    List<CallsDTO> getStartedCalls();
    List<CallsDTO> getSolveCalls();
    List<CallsDTO> getNotSolveCalls();
    List<CallsDTO> getListOfCallsFillter(long id , java.sql.Date date);

    List<CallsDTO> getKeywordsByCallId(long id);

    List<CallsDTO> getnerTagsByCallId(long id);
    List<CallsDTO> getEntityClassesByCallId(long id);

    List<CallsDTO> getDateByCallId(long id);
    void addCall(MultipartFile file) throws IOException;

}
