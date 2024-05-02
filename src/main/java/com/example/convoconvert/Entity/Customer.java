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
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "identityNumber", nullable = true)
    private int identityNumber;

    @Column(name = "phoneNumber", nullable = false)
    private int phoneNumber;

    @Column(name = "secondPhoneNumber", nullable = true)
    private int secondPhoneNumber;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "dateOfBirth", nullable = true)
    private Date dateOfBirth;

    @Column(name = "age", nullable = true)
    private int age;

    @Column(name = "regestrationDate", nullable = true)
    private Date regestrationDate;

    @Column(name = "subscriptionPlan", nullable = true)
    private String subscriptionPlan;

    @Column(name = "customerType", nullable = true)
    private String customerType;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "accountStatus", nullable = true)
    private String accountStatus;

}
