package com.example.convoconvert.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private int customerID;
    private String name;
    private int identityNumber;
    private int phoneNumber;
    private int secondPhoneNumber;
    private String address;
    private String email;
    private Date dateOfBirth;
    private int age;
    private Date regestrationDate;
    private String subscriptionPlan;
    private String customerType;
    private String gender;
    private String accountStatus;

}
