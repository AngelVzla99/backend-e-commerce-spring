package com.example.springboot.Notification.services;

import com.example.springboot.Notification.Exceptions.TemplateNotFound;
import com.example.springboot.Notification.clients.SmtlClient;
import com.example.springboot.User.dto.UserDTO;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class NotificationService {
    @Autowired
    SmtlClient emailClient;
    @Value("${base.url.frontend}")
    private String baseUrlFrontEnd;

    public String readFile(String name) throws TemplateNotFound {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            String filePrefix = "src/main/java/com/example/springboot/Notification/templates/";
            BufferedReader in = new BufferedReader(new FileReader(filePrefix+name+".html"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            throw new TemplateNotFound("Template with name "+name+" was not found");
        }
        return contentBuilder.toString();
    }

    public void sendEmail(String htmlTemplate, String toEmail, String subject) throws MessagingException, TemplateNotFound {
        emailClient.sendEmailFromTemplate(htmlTemplate,subject,toEmail);
    }

    public void sendResetPasswordEmail(String toEmail, UserDTO user, String token) throws MessagingException, TemplateNotFound {
        String templateName = "resetPassword";
        String subject = "Reinicar contraseña";

        // Read the HTML template into a String variable
        String htmlTemplate = this.readFile(templateName);

        // Replace placeholders in the HTML template with dynamic values
        htmlTemplate = htmlTemplate.replace("{{userName}}", user.getFirstName());
        htmlTemplate = htmlTemplate.replace("{{expirationTime}}", "2 días");
        htmlTemplate = htmlTemplate.replace("{{companyName}}", "Automercados supremo");
        htmlTemplate = htmlTemplate.replace("{{resetPasswordUrl}}", this.baseUrlFrontEnd+"/reset-password?token="+token+"&email="+user.getEmail());

        this.sendEmail(htmlTemplate,toEmail,subject);
    }

}
