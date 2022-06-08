package com.example.smtpdemo.controller;

import com.example.smtpdemo.service.EmailServiceImpl;
import com.example.smtpdemo.modal.SimpleMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private EmailServiceImpl emailService;

    @Autowired
    public void setEmailService(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email-reset-password")
    public void sendEmailResetPassword(@RequestBody SimpleMail simpleMail) {
        emailService.sendEmailResetPassword(simpleMail.getTo());
    }
}
