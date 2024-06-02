package com.example.convoconvert.Controller;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.DTO.CustomerDTO;
import com.example.convoconvert.DTO.EmployeeDTO;
import com.example.convoconvert.Exception.BadRequestException;
import com.example.convoconvert.Service.Interface.CustomerServiceInterface;
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
@RequestMapping("/Customer")
public class CustomerController {

    @Autowired
    private CustomerServiceInterface customerServiceInterface;
    private final Logger log = LoggerFactory.getLogger(CustomerController.class);
    @CrossOrigin
    @GetMapping("/get")
    public ResponseEntity<CustomerDTO> getCustomerById(@RequestParam long id){

        return ResponseEntity.ok(customerServiceInterface.getCustomerById(id));
    }
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomer (){

        return ResponseEntity.ok().body(customerServiceInterface.getAllCustomer());
    }
    @CrossOrigin
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer (@Valid @RequestBody CustomerDTO customerDTO) {

        if (customerDTO.getName() ==null) {
            log.error("Cannot have an name {}", customerDTO);
            throw new BadRequestException(CustomerController.class.getSimpleName(),
                    "Name");
        }

        return ResponseEntity.ok().body(customerServiceInterface.createCustomer(customerDTO));

    }
    @CrossOrigin
    @PutMapping ("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer
            (@Valid @RequestBody CustomerDTO customerDTO
                    , @PathVariable(name = "id") long id) {

        return new ResponseEntity<>(customerServiceInterface.updateCustomer(customerDTO, id), HttpStatus.OK);
    }
    @CrossOrigin
    @PatchMapping ("/{id}")
    public ResponseEntity<CustomerDTO> updateFieldsOfCustomer
            (@Valid @RequestBody Map<String , Optional> map
                    , @PathVariable(name = "id") long id) {

        return new ResponseEntity<>(customerServiceInterface.updateFieldsOfCustomer(id , map), HttpStatus.OK);
    }
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer (@PathVariable(name = "id") long id){
        customerServiceInterface.deleteCustomerById(id);
        return new ResponseEntity<>("Deleted Customer was successful!", HttpStatus.OK);
    }
}

