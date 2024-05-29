package com.example.convoconvert.Controller;


import com.example.convoconvert.DTO.CustomerDTO;
import com.example.convoconvert.DTO.EmployeeDTO;
import com.example.convoconvert.Exception.BadRequestException;
import com.example.convoconvert.Service.EmployeeService;
import com.example.convoconvert.Service.Interface.EmployeeServiceInterface;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.ID;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/Employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceInterface employeeServiceInterface;
    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @CrossOrigin
    @GetMapping("/get")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@RequestParam long id){

        return ResponseEntity.ok(employeeServiceInterface.getEmployeeById(id));
    }
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee (){

        return ResponseEntity.ok().body(employeeServiceInterface.getAllEmployee());
    }
    @CrossOrigin
    @PostMapping
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
    public ResponseEntity<EmployeeDTO> updateEmployee
            (@Valid @RequestBody EmployeeDTO employeeDTO
                    , @PathVariable(name = "id") long id) {

        return new ResponseEntity<>(employeeServiceInterface.updateEmployee(employeeDTO, id), HttpStatus.OK);
    }

   /* @PutMapping("/{name}")
    public ResponseEntity<EmployeeDTO> updateEmployeeByName(
            @Valid @RequestBody EmployeeDTO employeeDTO,
            @PathVariable(name = "name") String name) {

      //  return new ResponseEntity<>(employeeServiceInterface.updateEmployee(employeeDTO, name), HttpStatus.OK);
    }*/
   @CrossOrigin
    @PatchMapping ("/{id}")
    public ResponseEntity<EmployeeDTO> updateFieldsOfEmployee
            (@Valid @RequestBody Map<String , Optional> map
                    , @PathVariable(name = "id") long id) {

        return new ResponseEntity<>(employeeServiceInterface.updateFieldsOfEmployee(id , map), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee (@PathVariable(name = "id") long id){
        employeeServiceInterface.deleteEmployeeById(id);
        return new ResponseEntity<>("Deleted successfully!", HttpStatus.OK);
    }


}
