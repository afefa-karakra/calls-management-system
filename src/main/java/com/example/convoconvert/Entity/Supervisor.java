package com.example.convoconvert.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Supervisor")
public class Supervisor  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupervisorID")
    private long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "PhoneNumber", nullable = false)
    private int PhoneNumber;
}