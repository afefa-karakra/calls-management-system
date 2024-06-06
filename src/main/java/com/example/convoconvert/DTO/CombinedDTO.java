package com.example.convoconvert.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinedDTO {

    private CallsDTO calls;
    private CustomerDTO customer;
    private EmployeeDTO employee;
}
