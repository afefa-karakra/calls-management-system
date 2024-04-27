package com.example.convoconvert.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "PhoneNumber", nullable = false)
    private int phoneNumber;

    @Column(name = "identityNumber", nullable = false)
    private int identityNumber;

    @Column(name = "secondPhoneNumber", nullable = false)
    private int secondPhoneNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "dateOfBirth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "regestrationDate", nullable = false)
    private Date regestrationDate;

    @Column(name = "subscriptionPlan", nullable = false)
    private String subscriptionPlan;

    @Column(name = "customerType", nullable = false)
    private String customerType;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "accountStatus", nullable = false)
    private String accountStatus;



}
