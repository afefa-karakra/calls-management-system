package com.example.convoconvert.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private long id;
    private String name;
    private int identityNumber;
    private int phoneNumber;
    private int secondPhoneNumber;
    private String address;
    private String email;
    private String department;
    private String position;
    private Date hireDate;
    private String shiftInformation;
    private Time averageCallDuration;
    private double salaryPerM;
    private Date dateOfBirth;
    private int age;
    private String gender;
    private int vacationDaysPerM;
    private String expertise;
    private String comments;
}
