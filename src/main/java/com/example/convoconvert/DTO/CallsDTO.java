package com.example.convoconvert.DTO;

import java.sql.Blob;
import java.sql.Time;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.ConstructorParameters;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallsDTO {
    private long id;
    private String audio;
    private String audioText;
    private String textEntities;
    private Time time;
    private Date date;
    private boolean started;
    private String status;
    private String keywords;
    private String nerTags;
    private String entityClasses;
    private boolean trash;

}
