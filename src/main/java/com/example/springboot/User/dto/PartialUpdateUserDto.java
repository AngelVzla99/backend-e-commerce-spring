package com.example.springboot.User.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartialUpdateUserDto {
    @NotBlank(message = "User first name is required")
    @Size(min = 2, max = 50, message = "The first name must be between 2 and 50 characters")
    private  String firstName;
    @Size(min = 2, max = 50, message = "The last name must be between 2 and 50 characters")
    @NotBlank(message = "User last name is required")
    private  String lastName;
    @NotBlank(message = "The email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 50, message = "The email must have at most 50 characters")
    private  String email;
    @Size(min = 2, max = 50, message = "The phone number must be between 2 and 50 characters")
    @NotBlank(message = "The phone number is required")
    private  String phoneNumber;
    private String password = null;
    private String resetPasswordToken = null;
}
