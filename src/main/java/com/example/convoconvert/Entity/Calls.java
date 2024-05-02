package com.example.convoconvert.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Calls")

public class Calls implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "callsID")
    private long callsID;

    @Column(name = "audio", nullable = true)
    private String audio;

    @Column(name = "audioText", nullable = false)
    private String audioText;

    @Column(name = "textEntities", nullable = true)
    private String textEntities;

    @Column(name = "time", nullable = true)
    private Time time;

    @Column(name = "date", nullable = true)
    private Date date;

    @Column(name = "started", nullable = true)
    private boolean started;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "keywords", nullable = true)
    private String keywords;

    @Column(name = "nerTags", nullable = true)
    private String nerTags;

    @Column(name = "entityClasses", nullable = true)
    private String entityClasses;

    @Column(name = "trash", nullable = true)
    private boolean trash;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerIdFK", referencedColumnName = "customerID")
    private Customer customer;

   /* @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeIdFK", referencedColumnName = "employeeID")
    private Employee employee;*/
}
