package com.example.convoconvert.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID")
    private long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "PhoneNumber", nullable = false)
    private int PhoneNumber;


    @Column(name = "identityNumber", nullable = false)
    private int identityNumber;

    @Column(name = "phoneNumber", nullable = false)
    private int phoneNumber;

    @Column(name = "secondPhoneNumber", nullable = false)
    private int secondPhoneNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "hireDate", nullable = false)
    private Date hireDate;

    @Column(name = "shiftInformation", nullable = false)
    private String shiftInformation;

    @Column(name = "averageCallDuration", nullable = false)
    private Time averageCallDuration;

    @Column(name = "salaryPerM", nullable = false)
    private double salaryPerM;

    @Column(name = "dateOfBirth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "vacationDaysPerM", nullable = false)
    private int vacationDaysPerM;

    @Column(name = "expertise", nullable = false)
    private String expertise;

    @Column(name = "comments", nullable = false)
    private String comments;


}
