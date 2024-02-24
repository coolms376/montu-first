package com.school.mailservice;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

@Service
public class MailProcessingService {

    JavaMailSender javaMailSender;

    ApplicationService applicationService;

    SpringTemplateEngine templateResolver;


    @Autowired
    public MailProcessingService(JavaMailSender javaMailSender, SpringTemplateEngine templateResolver, ApplicationService applicationService) {
        this.javaMailSender = javaMailSender;
        this.templateResolver = templateResolver;
        this.applicationService = applicationService;
    }

    public ResponseEntity sendMail(String name, String email){
        try{
            SimpleMailMessage message =  getMessage(name,email);
            this.javaMailSender.send(message);
        }
        catch (Exception exception){
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("Error In Sending mail");
        }
        return ResponseEntity.ok("Mail Sent succesfully");
    }


    private SimpleMailMessage getMessage(String name,String email) throws IOException, URISyntaxException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("AnandSinghYadav@asy.com");
        message.setTo(email);
        message.setReplyTo("coolms376@gmail.com");
        message.setSubject("Assignment Submission");
        message.setText(applicationService.get(name));
        return message;
    }

    private MimeMessage getMimeMessage(String name,String email) throws IOException, MessagingException {
        Properties props = new Properties();
        String host = "smtp-relay.brevo.com";
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("ms346k@gmail.com", "tL89pER2KnfATjzw");
                    }
                });
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
        mimeMessage.setSender(new InternetAddress("AnandSinghYadav@asy.com"));
        mimeMessage.setSubject("Assignment Submission");
        mimeMessage.setContent(applicationService.get(name),"test/html");
        return mimeMessage;
    }
}
