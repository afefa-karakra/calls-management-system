package com.example.convoconvert.Controller;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.DTO.EmployeeDTO;
import com.example.convoconvert.Exception.BadRequestException;
import com.example.convoconvert.Service.Interface.CallsServiceInterface;
import com.example.convoconvert.Service.Interface.EmployeeServiceInterface;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Calls")
public class CallsController {
    @Autowired
    private CallsServiceInterface callsServiceInterface;
    private final Logger log = LoggerFactory.getLogger(CallsController.class);

    @GetMapping("/get")
    public ResponseEntity<CallsDTO> getCallsById(@RequestParam long id){

        return ResponseEntity.ok(callsServiceInterface.getCallById(id));
    }

    @GetMapping
    public ResponseEntity<List<CallsDTO>> getAllCalls (){

        return ResponseEntity.ok().body(callsServiceInterface.getAllCalls());
    }

    @PostMapping
    public ResponseEntity<CallsDTO> createCalls (@Valid @RequestBody CallsDTO callsDTO) {

        if (callsDTO.getAudioText() ==null) {
            log.error("Cannot have an AudioText {}", callsDTO);
            throw new BadRequestException(CallsController.class.getSimpleName(),
                    "Name");
        }

        return ResponseEntity.ok().body(callsServiceInterface.createCall(callsDTO));
}
    @PutMapping ("/{id}")
    public ResponseEntity<CallsDTO> updateCallls
            (@Valid @RequestBody CallsDTO callsDTO
                    , @PathVariable(name = "id") long id) {

        return new ResponseEntity<>(callsServiceInterface.updateCall(callsDTO, id), HttpStatus.OK);
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<CallsDTO> updateFieldsOfCallls
            (@Valid @RequestBody Map<String , Optional> map
                    , @PathVariable(name = "id") long id) {

        return new ResponseEntity<>(callsServiceInterface.updateFieldsOfCall(id , map), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCalls (@PathVariable(name = "id") long id){
        callsServiceInterface.deleteCallById(id);
        return new ResponseEntity<>("Deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/started")
    public ResponseEntity<List<CallsDTO>> getStartedCalls(){
        return ResponseEntity.ok().body(callsServiceInterface.getStartedCalls());
    }
}
