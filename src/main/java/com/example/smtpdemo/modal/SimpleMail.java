package com.example.smtpdemo.modal;

import lombok.Data;

@Data
public class SimpleMail {

    private String to;
    private String subject;
    private String text;
}
