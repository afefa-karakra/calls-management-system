package com.example.convoconvert.Service.Interface;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.DTO.CustomerDTO;
import com.example.convoconvert.DTO.CustomerDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomerServiceInterface {

    CustomerDTO getCustomerById (long id);
    CustomerDTO createCustomer (CustomerDTO customerDTO);
    CustomerDTO updateCustomer (CustomerDTO customerDTO , long id);

    CustomerDTO updateFieldsOfCustomer (long id , Map<String , Optional> map);
    List<CustomerDTO> getAllCustomer();
    void deleteCustomerById (long id);

  //  List<CustomerDTO> getAllCustomerName();


}
