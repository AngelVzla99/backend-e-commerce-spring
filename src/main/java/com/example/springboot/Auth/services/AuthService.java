package com.example.springboot.Auth.services;

import com.example.springboot.Auth.dtos.ResetPasswordDto;
import com.example.springboot.Notification.Exceptions.TemplateNotFound;
import com.example.springboot.Notification.services.NotificationService;
import com.example.springboot.User.adapters.RandomStringAdapter;
import com.example.springboot.User.dto.PartialUpdateUserDto;
import com.example.springboot.User.dto.UserDTO;
import com.example.springboot.User.services.UserService;
import jakarta.mail.MessagingException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Suspendable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserService userService;

    public void resetPassword(ResetPasswordDto dto){
        UserDTO user = userService.findByEmail(dto.getEmail());

        if( !user.getResetPasswordToken().equals(dto.getToken()) ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid token");
        }

        user.setResetPasswordToken(null);
        user.setPassword(dto.getNewPassword());
        PartialUpdateUserDto updateUserDto = new PartialUpdateUserDto();
        updateUserDto.setResetPasswordToken(null);
        updateUserDto.setPassword(dto.getNewPassword());

        userService.partialUpdate(user.getId(),updateUserDto);
    }

    public void sendResetPasswordEmail(String email) throws MessagingException, TemplateNotFound {
        String token = new RandomStringAdapter(25).nextString();
        UserDTO user = userService.findByEmail(email);

        PartialUpdateUserDto dto = new PartialUpdateUserDto();
        dto.setResetPasswordToken(token);
        userService.partialUpdate(user.getId(),dto);
        notificationService.sendResetPasswordEmail(email,user,token);
    }
}
