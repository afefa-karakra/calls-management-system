package com.example.convoconvert.Controller;

import com.example.convoconvert.DTO.EmployeeDTO;
import com.example.convoconvert.Exception.BadRequestException;
import com.example.convoconvert.Service.Interface.EmployeeServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Employee")
@RestController
@RequestMapping("/Employee")
public class EmployeeController {


    private EmployeeServiceInterface employeeServiceInterface;

    public EmployeeController(EmployeeServiceInterface employeeServiceInterface) {
        this.employeeServiceInterface = employeeServiceInterface;
    }

    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @CrossOrigin
    @GetMapping("/get")
    @Operation(
           // security = @SecurityRequirement(name = "token"),
            description = "Get Employee by id service",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully get Employee by id!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Successfully get Employee by id!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<EmployeeDTO> getEmployeeById(@RequestParam long id){

        return ResponseEntity.ok(employeeServiceInterface.getEmployeeById(id));
    }
    @CrossOrigin
    @GetMapping
    @Operation(
          //  security = @SecurityRequirement(name = "token"),
            description = "Get all Employee service",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully get all Employee!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Successfully get all Employee!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee (){

        return ResponseEntity.ok().body(employeeServiceInterface.getAllEmployee());
    }
    @CrossOrigin
    @PostMapping
    @Operation(
           // security = @SecurityRequirement(name = "token"),
            description = "Create Employee service",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully create Employee!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 201, \"Status\" : \"Created!\", \"Message\" :\"Successfully create Employee!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<EmployeeDTO> createEmployee (@Valid @RequestBody EmployeeDTO employeeDTO) {

        if (employeeDTO.getName() ==null) {
            log.error("Cannot have an name {}", employeeDTO);
            throw new BadRequestException(EmployeeController.class.getSimpleName(),
                    "Name");
        }

        return ResponseEntity.ok().body(employeeServiceInterface.createEmployee(employeeDTO));
        //return new ResponseEntity(employeeServiceInterface.createEmployee(employeeDTO), HttpStatus.CREATED);

    }
    @CrossOrigin
    @PutMapping ("/{id}")
    @Operation(
          //  security = @SecurityRequirement(name = "token"),
            description = "Update Employee info service",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Successfully Update Employee info!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 202, \"Status\" : \"Accepted!\", \"Message\" :\"Successfully Update Employee info!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<EmployeeDTO> updateEmployee
            (@Valid @RequestBody EmployeeDTO employeeDTO
                    , @PathVariable(name = "id") long id) {

        return new ResponseEntity<>(employeeServiceInterface.updateEmployee(employeeDTO, id), HttpStatus.OK);
    }
   @CrossOrigin
    @PatchMapping ("/{id}")
    public ResponseEntity<EmployeeDTO> updateFieldsOfEmployee
            (@Valid @RequestBody Map<String , Optional> map
                    , @PathVariable(name = "id") long id) {

        return new ResponseEntity<>(employeeServiceInterface.updateFieldsOfEmployee(id , map), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @Operation(
           // security = @SecurityRequirement(name = "token"),
            description = "Delete Employee service",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully delete Employee!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Accepted!\", \"Message\" :\"Successfully delete Employee!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<String> deleteEmployee (@PathVariable(name = "id") long id){
        employeeServiceInterface.deleteEmployeeById(id);
        return new ResponseEntity<>("Deleted successfully!", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/names")
    public List<String> getAllEmployeeNames() {
        return employeeServiceInterface.getAllEmployeeNames();
    }

}
