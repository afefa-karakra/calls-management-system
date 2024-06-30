package com.example.convoconvert.DTO;

import java.sql.Time;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallsDTO {
    private long id;
    private String audio;
    private String audioText;
    private Time time;
    private Date date;
    private boolean started;
    private String status;
    private String keywords;
    private String nerTags;
    private String entityClasses;
    private boolean trash;
    private String employeeName;
    private String customerName;


    private int phoneNumber;
    private int secondPhoneNumber;

    public CallsDTO(String keywords) {
        this.keywords = keywords;
    }


}
