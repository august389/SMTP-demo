package com.example.smtpdemo.modal;

import lombok.Data;

import java.util.Map;

@Data
public class Email {

    private String to;
    private String subject;
    private String text;
    private String template;
    private Map<String, Object> properties;
}
