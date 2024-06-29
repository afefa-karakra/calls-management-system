package com.example.convoconvert.Controller;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.Exception.BadRequestException;
import com.example.convoconvert.Service.Interface.CallsServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Calls")
@RestController
@RequestMapping("/Calls")
public class CallsController {
   final private CallsServiceInterface callsServiceInterface;

    public CallsController(CallsServiceInterface callsServiceInterface) {
        this.callsServiceInterface = callsServiceInterface;
    }

    private final Logger log = LoggerFactory.getLogger(CallsController.class);

    @CrossOrigin
    @GetMapping("/get")
    @Operation(
           // security = @SecurityRequirement(name = "token"),
            description = "Get Calls by id service",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully get Calls by id!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Successfully get Calls by id!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<CallsDTO> getCallsById(@RequestParam long id){

        return ResponseEntity.ok(callsServiceInterface.getCallById(id));
    }


   // @ApiOperation(value = "Get Billing by ID")
    @CrossOrigin
    @GetMapping

    @Operation(
           // security = @SecurityRequirement(name = "token"),
            description = "Get Calls service",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully get Calls by id!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Successfully get Calls by id!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<List<CallsDTO>> getAllCalls (){
        return ResponseEntity.ok().body(callsServiceInterface.getAllCalls());
    }

    @CrossOrigin
    @PostMapping
    @Operation(
          //  security = @SecurityRequirement(name = "token"),
            description = "Create Calls service",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully create Calls!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 201, \"Status\" : \"Created!\", \"Message\" :\"Successfully create Calls!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
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
    @Operation(
         //   security = @SecurityRequirement(name = "token"),
            description = "Update Calls info service",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Successfully Update Calls info!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 202, \"Status\" : \"Accepted!\", \"Message\" :\"Successfully Update Billing info!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
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
    @PostMapping("/add")
    public void convertSpeechToText(@RequestParam("file") MultipartFile file) throws IOException {
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
//        }
        callsServiceInterface.addCall(file);

    }

}
