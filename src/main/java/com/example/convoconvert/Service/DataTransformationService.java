package com.example.convoconvert.Service;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.DTO.CombinedDTO;
import com.example.convoconvert.DTO.CustomerDTO;
import com.example.convoconvert.DTO.EmployeeDTO;
import com.example.convoconvert.Entity.Calls;
import com.example.convoconvert.Entity.Customer;
import com.example.convoconvert.Entity.Employee;
import com.example.convoconvert.Repository.CallsInterfaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataTransformationService  {


   @Autowired
    private CallsInterfaceRepository callsInterfaceRepository;


    public List<CombinedDTO> getTransformedData() {
        List<Object[]> rawData = callsInterfaceRepository.geDataWithEmployeeNameAndCustomerId();
        List<CombinedDTO> transformedData = new ArrayList<>();

        for (Object[] record : rawData) {
            Calls calls = (Calls) record[0];
            Customer customer = (Customer) record[1];
            Employee employee = (Employee) record[2];

            CombinedDTO combinedDTO = new CombinedDTO();
            combinedDTO.setCalls(mapToCallsDTO(calls));
            combinedDTO.setCustomer(mapToCustomerDTO(customer));
            combinedDTO.setEmployee(mapToEmployeeDTO(employee));

            transformedData.add(combinedDTO);
        }

        return transformedData;
    }


    private CallsDTO mapToCallsDTO(Calls calls) {
        CallsDTO callsDTO = new CallsDTO();
        callsDTO.setId(calls.getId());
        callsDTO.setAudioText(calls.getAudioText());
        callsDTO.setDate(calls.getDate());
        callsDTO.setAudio(Arrays.toString(calls.getAudio()));
        callsDTO.setTime(calls.getTime());
        callsDTO.setStarted(calls.isStarted());
        callsDTO.setStatus(calls.getStatus());
        callsDTO.setKeywords(calls.getKeywords());
        callsDTO.setNerTags(calls.getNerTags());
        callsDTO.setEntityClasses(calls.getEntityClasses());
        callsDTO.setTrash(calls.isTrash());
        return callsDTO;
    }

    private CustomerDTO mapToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setIdentityNumber(customer.getIdentityNumber());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setSecondPhoneNumber(customer.getSecondPhoneNumber());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setDateOfBirth(customer.getDateOfBirth());
        customerDTO.setAge(customer.getAge());
        customerDTO.setSubscriptionPlan(customer.getSubscriptionPlan());
        customerDTO.setCustomerType(customer.getCustomerType());
        customerDTO.setGender(customer.getGender());
        customerDTO.setAccountStatus(customer.getAccountStatus());
        return customerDTO;
    }

    private EmployeeDTO mapToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setIdentityNumber(employee.getIdentityNumber());
        employeeDTO.setPhoneNumber(employee.getPhoneNumber());
        employeeDTO.setSecondPhoneNumber(employee.getSecondPhoneNumber());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setDepartment(employee.getDepartment());
        employeeDTO.setPosition(employee.getPosition());
        employeeDTO.setHireDate(employee.getHireDate());
        employeeDTO.setShiftInformation(employee.getShiftInformation());
        employeeDTO.setAverageCallDuration(employee.getAverageCallDuration());
        employeeDTO.setSalaryPerM(employee.getSalaryPerM());
        employeeDTO.setDateOfBirth(employee.getDateOfBirth());
        employeeDTO.setAge(employee.getAge());
        employeeDTO.setGender(employee.getGender());
        employeeDTO.setVacationDaysPerM(employee.getVacationDaysPerM());
        employeeDTO.setExpertise(employee.getExpertise());
        employeeDTO.setComments(employee.getComments());
        return employeeDTO;
    }
}

