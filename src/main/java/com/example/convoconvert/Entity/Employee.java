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
    @Column(name = "employeeID")
    private long employeeID;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "identityNumber", nullable = true)
    private int identityNumber;

    @Column(name = "phoneNumber", nullable = true)
    private int phoneNumber;

    @Column(name = "secondPhoneNumber", nullable = true)
    private int secondPhoneNumber;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "department", nullable = true)
    private String department;

    @Column(name = "position", nullable = true)
    private String position;

    @Column(name = "hireDate", nullable = true)
    private Date hireDate;

    @Column(name = "shiftInformation", nullable = true)
    private String shiftInformation;

    @Column(name = "averageCallDuration", nullable = true)
    private Time averageCallDuration;

    @Column(name = "salaryPerM", nullable = true)
    private double salaryPerM;

    @Column(name = "dateOfBirth", nullable = true)
    private Date dateOfBirth;

    @Column(name = "age", nullable = true)
    private int age;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "vacationDaysPerM", nullable = true)
    private int vacationDaysPerM;

    @Column(name = "expertise", nullable = true)
    private String expertise;

    @Column(name = "comments", nullable = true)
    private String comments;


}
