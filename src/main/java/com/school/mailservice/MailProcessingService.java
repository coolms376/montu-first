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
import java.util.*;

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
            MimeMessage message = getMimeMessage(name,email);
            this.javaMailSender.send(message);

        }
        catch (Exception exception){
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("Error In Sending mail");
        }
        return ResponseEntity.ok("Mail Sent succesfully");
    }

    public ResponseEntity sendOTPMail(String name, String email){
        HashMap<String, Object> response = new HashMap<>();

        try{
            Random random = new Random();
            long otp  = random.nextLong(124567l,999999l);
            response.put("otp", otp);
            MimeMessage message = getMimeMessageOTP(name,email,otp);
            this.javaMailSender.send(message);
        }
        catch (Exception exception){
            exception.printStackTrace();
            return ResponseEntity.badRequest().body("Error In Sending mail");
        }
        response.put("message","Mail Sent succesfully");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity sendAssignmentSubmission(String name, String email, String assignmentTitle, String batch){
        try{
            MimeMessage message = getAssignmentSubmissionMimeMessage(name,email,assignmentTitle,batch);
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
        Multipart multipart = new MimeMultipart();
        MimeBodyPart bodyPart = new MimeBodyPart();
        jakarta.mail.internet.MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        mimeMessage.setFrom(new InternetAddress("anandyadav2332@gmail.com","Anand Singh Yadav"));
        mimeMessage.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(email,name)});
        mimeMessage.setSubject("Message from Instructor - Assignment Submission");
        mimeMessage.setReplyTo(new InternetAddress[]{new InternetAddress("anandyadav2332@gmail.com","Anand Singh Yadav")});
        bodyPart.setContent(applicationService.get(name),"text/html");
        multipart.addBodyPart(bodyPart);
        mimeMessage.setContent(multipart);
        return mimeMessage;
    }

    private MimeMessage getMimeMessageOTP(String name, String email, long otp) throws IOException, MessagingException {
        Multipart multipart = new MimeMultipart();
        MimeBodyPart bodyPart = new MimeBodyPart();
        jakarta.mail.internet.MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        mimeMessage.setFrom(new InternetAddress("OtpService@gmail.com","OTP Service"));
        mimeMessage.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(email,name)});
        mimeMessage.setSubject("Application OTP");
        mimeMessage.setReplyTo(new InternetAddress[]{new InternetAddress("montusharma376@gmail.com","OTP Service")});
        bodyPart.setContent(String.format("Your OTP is -  %s",otp),"text/html");
        multipart.addBodyPart(bodyPart);
        mimeMessage.setContent(multipart);
        return mimeMessage;
    }

    private MimeMessage getAssignmentSubmissionMimeMessage(String name,String email, String assignmentTitle, String batch) throws IOException, MessagingException {
        Multipart multipart = new MimeMultipart();
        MimeBodyPart bodyPart = new MimeBodyPart();
        jakarta.mail.internet.MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        mimeMessage.setFrom(new InternetAddress("anandyadav2332@gmail.com","Anand Singh Yadav"));
        mimeMessage.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(email,name)});
        mimeMessage.setSubject("Message from Instructor - Assignment Submission");
        mimeMessage.setReplyTo(new InternetAddress[]{new InternetAddress("anandyadav2332@gmail.com","Anand Singh Yadav")});
        bodyPart.setContent(applicationService.getAssignmentSubmission(name,assignmentTitle,batch),"text/html");
        multipart.addBodyPart(bodyPart);
        mimeMessage.setContent(multipart);
        return mimeMessage;
    }

}
