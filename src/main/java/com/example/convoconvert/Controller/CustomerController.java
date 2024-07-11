package com.example.convoconvert.Controller;

import com.example.convoconvert.DTO.CustomerDTO;
import com.example.convoconvert.Exception.BadRequestException;
import com.example.convoconvert.Service.Interface.CustomerServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Tag(name = "Customer")
@RestController
@RequestMapping("/Customer")
public class CustomerController {
   final private CustomerServiceInterface customerServiceInterface;

    public CustomerController(CustomerServiceInterface customerServiceInterface) {
        this.customerServiceInterface = customerServiceInterface;
    }

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);
    @CrossOrigin
    @GetMapping("/get")
    @Operation(
         //   security = @SecurityRequirement(name = "token"),
            description = "Get customer by id service",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully get customer by id!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Successfully get customer by id!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )

    public ResponseEntity<CustomerDTO> getCustomerById(@RequestParam long id){

        return ResponseEntity.ok(customerServiceInterface.getCustomerById(id));
    }
    @CrossOrigin
    @GetMapping
    @Operation(
          //  security = @SecurityRequirement(name = "token"),
            description = "Get all customers service",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully get all customers!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Successfully get all customers!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<List<CustomerDTO>> getAllCustomer (){

        return ResponseEntity.ok().body(customerServiceInterface.getAllCustomer());
    }
    @CrossOrigin
    @PostMapping
    @Operation(
       //     security = @SecurityRequirement(name = "token"),
            description = "Create customer service",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully create customers!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 201, \"Status\" : \"Created!\", \"Message\" :\"Successfully create customer!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
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
    @Operation(
        //    security = @SecurityRequirement(name = "token"),
            description = "Update customer info service",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Successfully Update customer info!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 202, \"Status\" : \"Accepted!\", \"Message\" :\"Successfully Update customer info!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
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
    @Operation(
         //   security = @SecurityRequirement(name = "token"),
            description = "Delete customer service",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully delete customer!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Accepted!\", \"Message\" :\"Successfully delete customer!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<String> deleteCustomer (@PathVariable(name = "id") long id){
        customerServiceInterface.deleteCustomerById(id);
        return new ResponseEntity<>("Deleted Customer was successful!", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/names")
    public List<String> getAllCustomerNames() {
        return customerServiceInterface.getAllCustomerNames();
    }
}

