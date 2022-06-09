package com.example.smtpdemo.controller;

import com.example.smtpdemo.service.EmailService;
import com.example.smtpdemo.modal.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    public JavaMailSender emailSender;
    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email-reset-password")
    public void sendEmailResetPassword(@RequestBody Email email) {
        emailService.sendEmailResetPassword(email.getTo());
    }

    @PostMapping("/send-email-reset-password-html")
    public void sendHtmlMessageResetPassword(@RequestBody Email email) {
        emailService.sendHtmlMessageResetPassword(email);
    }
}
