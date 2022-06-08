package com.example.smtpdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@Service
public class EmailServiceImpl {

    private JavaMailSender emailSender;

    @Autowired
    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmailResetPassword(String to) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(String.valueOf(new InternetAddress("address", "Ascend")));
            helper.setSubject("Forgot password?");
            helper.setTo(to);
            String url = "https://spring.io/";
            String htmlContent="";
            htmlContent+="<div style='font-family: Arial, Helvetica, sans-serif;text-align: center;'>";
            htmlContent+="<div><h2 style='font-weight: bold; font-style:italic; text-align: center;'>Forgot your password?</h2></div>";
            htmlContent+="<div><p style='padding-bottom: 30px;padding-top: 10px;font-size: 19px;'>To reset your password, click the button bellow.</p></div>";
            htmlContent+="<div><a style='width: 200px; height: 35px; color: white;background-color: #3c78d8;border:0px; border-radius: 1px; " +
                    "text-decoration: none;padding: 9px 60px;'href='"+ url +"'>Forgot your password</a></div>";
            htmlContent+="<div><div style='margin-top:200px; font-size: 12px; text-align: center;'>Lorem Ipsum is simply " +
                    "dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s</div></div>";
            htmlContent+="</div>";
            helper.setText(htmlContent, true);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("MessagingException");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
