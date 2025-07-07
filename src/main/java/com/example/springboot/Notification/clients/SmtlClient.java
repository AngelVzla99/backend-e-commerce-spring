package com.example.springboot.Notification.clients;

import com.example.springboot.Notification.Exceptions.TemplateNotFound;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SmtlClient {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendEmailFromTemplate(String htmlTemplate, String subject, String email) throws MessagingException, TemplateNotFound {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("sender@example.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(subject);
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }
}
