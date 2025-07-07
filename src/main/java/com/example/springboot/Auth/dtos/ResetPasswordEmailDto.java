package com.example.springboot.Auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPasswordEmailDto {
    @Email
    @NotNull(message = "email is required")
    private String email;
}
