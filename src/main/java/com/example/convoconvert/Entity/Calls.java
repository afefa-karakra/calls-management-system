package com.example.convoconvert.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import java.io.Serializable;
import java.sql.Time;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Calls")

public class Calls implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "audioText", nullable = false)
    private String audioText;

    @Column(name = "date", nullable = true)
    private Date date;

    @Column(name = "audio", nullable = true)
    private byte[] audio;

    @Column(name = "time", nullable = true)
    private Time time;

    @Column(name = "started")
    private boolean started;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "keywords", nullable = true)
    private String keywords;

    @Column(name = "nerTags", nullable = true)
    private String nerTags;

    @Column(name = "entityClasses", nullable = true)
    private String entityClasses;

    @Column(name = "trash")
    private boolean trash;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerIdFK")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeIdFK")
    private Employee employee;

}
