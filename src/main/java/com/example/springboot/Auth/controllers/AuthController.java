package com.example.springboot.Auth.controllers;

import com.example.springboot.Auth.LoginRequest;
import com.example.springboot.Auth.dtos.ResetPasswordDto;
import com.example.springboot.Auth.dtos.ResetPasswordEmailDto;
import com.example.springboot.Auth.services.AuthService;
import com.example.springboot.Notification.Exceptions.TemplateNotFound;
import com.example.springboot.User.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;

    // ===============
    //   post EPs   //
    // ===============

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody LoginRequest dto) {
        return userService.getUserToken(dto.getEmail(), dto.getPassword()
        );
    }

    @PostMapping("/reset-password")
    @ResponseBody
    public void resetPassword(@Valid @RequestBody ResetPasswordDto dto) {
        authService.resetPassword(dto);
    }

    // ==============
    //   get EPs   //
    // ==============

    @GetMapping("/reset-password-email")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void sendResetPasswordEmail(@Valid @RequestBody ResetPasswordEmailDto dto) throws MessagingException, TemplateNotFound {
        authService.sendResetPasswordEmail(dto.getEmail());
    }
}
