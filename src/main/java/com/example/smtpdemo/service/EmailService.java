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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
//            String htmlContent="";
//            htmlContent+="<div style='font-family: Arial, Helvetica, sans-serif; position: relative; left:30%; font-size: 16px; max-width: 40%;line-height: 24px;'>";
//            htmlContent+="<img style='padding:10px 0;' src='./logo_forgot_password.png' alt=''>";
//            htmlContent+="<p style='padding-bottom: 20px;padding-top: 10px;'>Someone recently request that the password be reset for sample@gmail.com " +
//                    "To reset your password, please click this button</p>";
//            htmlContent+="<a style='width: 228px; height: 35px; color: white;background-color: #359EFF;border:0px; border-radius: 8px;" +
//                    "text-decoration: none;padding: 6px 16px;display: flex;align-items: center; " +
//                    "justify-content: center;font-weight: 700; font-size: 16px;' href='"+ url+ "'>Reset password</a>";
//            htmlContent+="<p style='padding:20px 0px'>If this is a mistake, please ignore this email - your password will not be changed</p>";
//            htmlContent+="<p>Trouble clicking? Copy and paste this URL to your browser </br><a href='"+url+
//                    "' style='text-decoration: none;color: #359EFF; font-weight:600;'>"+ url + "</a></p>";
//            htmlContent+="</div>";
            String expiryAt= " 16 06 2022 ";
            String troubleLink= "spring.io";
            String htmlContent = "";
            htmlContent += "<div style='padding-left:30%; max-width: 55%;'><img src='https://firebasestorage.googleapis.com/v0/b/allnow-image.appspot.com/o/image%2Fall_now.png?alt=media&token=e9069852-c3b3-459a-8451-cca9b659a8e9' alt='' style='width:200px'></div>";
            htmlContent += "<div style='margin: 40px 0px; padding-left:30%; max-width: 55%;' class='red-color'>";
            htmlContent += "<p>There was recently a request to change the password for your account.<br>If you requested this change, set a new password here:</p></div>";
            htmlContent += "<a style='text-decoration: none; padding: 7px 70px; background-color: #004A98;color: white; border-radius: 8px;' href='" + url + "'>Reset password</a>";
            htmlContent+="<a style='width: 228px; height: 35px; color: white;background-color: #359EFF;border:0px; border-radius: 8px;" +
                    "text-decoration: none;padding: 6px 16px;display: flex;align-items: center; " +
                    "justify-content: center;font-weight: 700; font-size: 16px;' href='"+ url+ "'>Reset password</a>";
            htmlContent += "<div style='margin: 40px 0; padding-left:30%; max-width: 55%;'><p> Recovery link is vaild until " + expiryAt + "</p></div>";
            htmlContent += "<div style='padding-left:30%; max-width: 55%;'><p>If you did not make this request, you can ignore this email and your password will remain the same.</p></div>";
            htmlContent += "<div style='margin: 40px 0;padding-left:30%; max-width: 55%;'><p style='line-height: 25px;'>Trouble clicking? Copy and paste this URL to your browser <br> <a";
            htmlContent += "style='color:#1f87f4; text-decoration: none;' href='" + troubleLink + "'>" + troubleLink + "</a></p></div>";
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
                Map<String, Object> variables= new HashMap<>();
                variables.put("urlResetPassword", "https://translate.google.com/?hl=vi");
                variables.put("trouble", "https://console.firebase.google.com/?hl=vi");
                context.setVariables(variables);
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
//
//    public static void main(String[] args) throws ParseException {
////        Timestamp timestamp= new Timestamp("2022-06-17 08:37:08.564")
////        Timestamp time= new Timestamp(new Date("2022-06-17 08:37:08.564"));
//        String dateString= "2022-06-17 08:37:08.564";
////        SimpleDateFormat format= new SimpleDateFormat("dd MMM yyyy HH:mm");
////        SimpleDateFormat format1= new SimpleDateFormat("HH:mm");
////        System.out.println(format.parse(dateString));
////        System.out.println(format1.parse(dateString));
//
//        String pattern = "MMM dd, yyyy HH:mm:ss.SSSSSSSS";
//        String timestampAsString = "Nov 12, 2018 13:02:56.12345678";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//        LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(timestampAsString));
//        System.out.println(localDateTime);
//        Timestamp t= Timestamp.valueOf(dateString);
//        System.out.println(new SimpleDateFormat("dd MMM yyy 'at' HH:mm").format(t));
//    }
}
