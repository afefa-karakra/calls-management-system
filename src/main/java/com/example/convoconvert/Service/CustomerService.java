package com.example.convoconvert.Service;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.DTO.CustomerDTO;
import com.example.convoconvert.DTO.EmployeeDTO;
import com.example.convoconvert.Entity.Calls;
import com.example.convoconvert.Entity.Customer;
import com.example.convoconvert.Entity.Employee;
import com.example.convoconvert.Exception.ResourceNotFoundException;
import com.example.convoconvert.Repository.CallsInterfaceRepository;
import com.example.convoconvert.Repository.CustomerInterfaceRepository;
import com.example.convoconvert.Service.Interface.CustomerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements CustomerServiceInterface {

    @Autowired
    private CustomerInterfaceRepository customerInterfaceRepository;

    @Override
    public CustomerDTO getCustomerById(long id) {

        Customer customer = customerInterfaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return mapToDTO(customer);
    }
    @Override
    public List<CustomerDTO> getAllCustomer() {

        List<Customer> customers = customerInterfaceRepository.findAll();
        return customers.stream().map(customer -> mapToDTO(customer))
                .collect(Collectors.toList());
    }
    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        // convert DTO to entity
        Customer customer = mapToEntity(customerDTO);
        Customer newCustomer = customerInterfaceRepository.save(customer);

        // convert entity to DTO
        CustomerDTO customerResponse = mapToDTO(newCustomer);
        return customerResponse;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, long id) {
        Customer customer = customerInterfaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        Customer updateCustomer = customerInterfaceRepository.save(customer);
        return mapToDTO(updateCustomer);
    }

    @Override
    public CustomerDTO updateFieldsOfCustomer(long id, Map<String, Optional> map) {
        Customer customer = customerInterfaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));


        for (Map.Entry<String , Optional> hm : map.entrySet()){
            String keyFromMap = hm.getKey();

            if(keyFromMap.equals("name")){
                String obj = hm.getValue().toString();

                customer.setName(obj);
            }

            if(keyFromMap.equals("PhoneNumber")){
                Integer obj = Integer.valueOf(String.valueOf(hm.getValue()));

                customer.setPhoneNumber(obj);
            }
        }
        Customer updateCustomer = customerInterfaceRepository.save(customer);
        return mapToDTO(updateCustomer);
    }

    @Override
    public void deleteCustomerById(long id) {

        Customer customer = customerInterfaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        customerInterfaceRepository.delete(customer);
    }

    private Customer mapToEntity (CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        return  customer;

    }

    private CustomerDTO mapToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerID(customer.getCustomerID());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());

        return  customerDTO;
    }
}
