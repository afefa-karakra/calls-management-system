package com.example.convoconvert.Service.Interface;

import com.example.convoconvert.DTO.CustomerDTO;
import com.example.convoconvert.DTO.EmployeeDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeServiceInterface {
    EmployeeDTO getEmployeeById (long id);
    EmployeeDTO createEmployee (EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee (EmployeeDTO employeeDTO , long id);
    EmployeeDTO updateFieldsOfEmployee (long id , Map<String , Optional> map);
    List<EmployeeDTO> getAllEmployee();
    void deleteEmployeeById (long id);
    List<String> getAllEmployeeNames();

}
