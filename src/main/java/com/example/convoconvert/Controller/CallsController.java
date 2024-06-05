package com.example.convoconvert.Controller;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.Exception.BadRequestException;
import com.example.convoconvert.Service.Interface.CallsServiceInterface;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/Calls")
public class CallsController {
    @Autowired
    private CallsServiceInterface callsServiceInterface;
    private final Logger log = LoggerFactory.getLogger(CallsController.class);

    @CrossOrigin
    @GetMapping("/get")
    public ResponseEntity<CallsDTO> getCallsById(@RequestParam long id){

        return ResponseEntity.ok(callsServiceInterface.getCallById(id));
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<CallsDTO>> getAllCalls (){

        return ResponseEntity.ok().body(callsServiceInterface.getAllCalls());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<CallsDTO> createCalls (@Valid @RequestBody CallsDTO callsDTO) {

        if (callsDTO.getAudioText() ==null) {
            log.error("Cannot have an AudioText {}", callsDTO);
            throw new BadRequestException(CallsController.class.getSimpleName(),
                    "Name");
        }

        return ResponseEntity.ok().body(callsServiceInterface.createCall(callsDTO));
}
    @CrossOrigin
    @PutMapping ("/{id}")
    public ResponseEntity<CallsDTO> updateCallls
            (@Valid @RequestBody CallsDTO callsDTO
                    , @PathVariable(name = "id") long id) {

        return new ResponseEntity<>(callsServiceInterface.updateCall(callsDTO, id), HttpStatus.OK);
    }

    @CrossOrigin
    @PatchMapping ("/{id}")
    public ResponseEntity<CallsDTO> updateFieldsOfCallls
            (@Valid @RequestBody Map<String , Optional> map
                    , @PathVariable(name = "id") long id) {

        return new ResponseEntity<>(callsServiceInterface.updateFieldsOfCall(id , map), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCalls (@PathVariable(name = "id") long id){
        callsServiceInterface.deleteCallById(id);
        return new ResponseEntity<>("Deleted successfully!", HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/started")
    public ResponseEntity<List<CallsDTO>> getStartedCalls(){
        return ResponseEntity.ok().body(callsServiceInterface.getStartedCalls());
    }

    @CrossOrigin
    @GetMapping("/solved")
    public ResponseEntity<List<CallsDTO>> getSolveCalls(){
        return ResponseEntity.ok().body(callsServiceInterface.getSolveCalls());
    }

    @CrossOrigin
    @GetMapping("/notsolved")
    public ResponseEntity<List<CallsDTO>> getNotSolveCalls(){
        return ResponseEntity.ok().body(callsServiceInterface.getNotSolveCalls());
    }

    @CrossOrigin
    @GetMapping("/fillter")
    public ResponseEntity<List<CallsDTO>> getListOfCallsFillter(@RequestParam long id ,@RequestParam("date")Date date){
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
       // return ResponseEntity.ok().body(callsServiceInterface.getListOfCallsFillter(id, (java.sql.Date) date));
        return ResponseEntity.ok().body(callsServiceInterface.getListOfCallsFillter(id, sqlDate));
    }

    @GetMapping("filter")
    public ResponseEntity<List<CallsDTO>> getFilteredCalls(@RequestParam("employeeId") long employeeId, @RequestParam("callDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) java.sql.Date callDate) {
        List<CallsDTO> callsList = callsServiceInterface.getListOfCallsFillter(employeeId, callDate);
        return ResponseEntity.ok(callsList);
    }


    @CrossOrigin
    @GetMapping("/keywords")
    public ResponseEntity<List<CallsDTO>> getkeywordsCalls(@RequestParam long id){
        return ResponseEntity.ok().body(callsServiceInterface.getKeywordsByCallId( id));
    }
    @CrossOrigin
    @GetMapping("/nerTags")
    public ResponseEntity<List<CallsDTO>> getnerTagsByCallId(@RequestParam long id){
        return ResponseEntity.ok().body(callsServiceInterface.getnerTagsByCallId( id));
    }

    @CrossOrigin
    @GetMapping("/entityClasses")
    public ResponseEntity<List<CallsDTO>> getEntityClassesByCallId(@RequestParam long id){
        return ResponseEntity.ok().body(callsServiceInterface.getEntityClassesByCallId( id));
    }
    @CrossOrigin
    @GetMapping("/date")
    public ResponseEntity<List<CallsDTO>> getDateByCallId(@RequestParam long id){
        return ResponseEntity.ok().body(callsServiceInterface.getDateByCallId( id));
    }

    @CrossOrigin
    @GetMapping("CallsSpecific")
    public ResponseEntity<List<Object[]>> geDataWithEmployeeNameAndCustomerId (){

        return ResponseEntity.ok().body(callsServiceInterface.geDataWithEmployeeNameAndCustomerId());
    }

}
