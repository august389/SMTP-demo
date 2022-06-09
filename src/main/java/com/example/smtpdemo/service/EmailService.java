package com.example.smtpdemo.service;

import com.example.smtpdemo.modal.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private JavaMailSender emailSender;

    private SpringTemplateEngine templateEngine;

    @Autowired
    public void setTemplateEngine(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Autowired
    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmailResetPassword(String to) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(String.valueOf(new InternetAddress("address", "CP TMS")));
            helper.setSubject("Reset your password");
            helper.setTo(to);
            String url = "https://spring.io/";
            String htmlContent="";
            htmlContent+="<div style='font-family: Arial, Helvetica, sans-serif; position: relative; left:30%; font-size: 16px; max-width: 40%;line-height: 24px;'>";
            htmlContent+="<img style='padding:10px 0;' src='./logo_forgot_password.png' alt=''>";
            htmlContent+="<p style='padding-bottom: 20px;padding-top: 10px;'>Someone recently request that the password be reset for sample@gmail.com " +
                    "To reset your password, please click this button</p>";
            htmlContent+="<a style='width: 228px; height: 35px; color: white;background-color: #359EFF;border:0px; border-radius: 8px;" +
                    "text-decoration: none;padding: 6px 16px;display: flex;align-items: center; " +
                    "justify-content: center;font-weight: 700; font-size: 16px;' href='"+ url+ "'>Reset password</a>";
            htmlContent+="<p style='padding:20px 0px'>If this is a mistake, please ignore this email - your password will not be changed</p>";
            htmlContent+="<p>Trouble clicking? Copy and paste this URL to your browser </br><a href='"+url+
                    "' style='text-decoration: none;color: #359EFF; font-weight:600;'>"+ url + "</a></p>";
            htmlContent+="</div>";
            helper.setText(htmlContent, true);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("MessagingException");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendHtmlMessageResetPassword(Email email){
            MimeMessage message= emailSender.createMimeMessage();
            try {
                MimeMessageHelper helper= new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
                Context context= new Context();
                context.setVariables(email.getProperties());
                helper.setFrom(String.valueOf(new InternetAddress("address", "CP TMS")));
                helper.setSubject("Reset your password");
                helper.setTo(email.getTo());
                String html= templateEngine.process("welcome-email.html", context);
                helper.setText(html, true);
                emailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

    }
}
