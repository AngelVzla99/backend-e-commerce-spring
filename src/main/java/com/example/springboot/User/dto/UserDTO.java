package com.example.springboot.User.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UserDTO {
    private Long id;
    @NotBlank(message = "User first name is required")
    @Size(min = 2, max = 50, message = "The first name must be between 2 and 50 characters")
    private  String firstName;
    @Size(min = 2, max = 50, message = "The last name must be between 2 and 50 characters")
    @NotBlank(message = "User last name is required")
    private  String lastName;
    private Date createdAt;
    @NotBlank(message = "The email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 50, message = "The email must have at most 50 characters")
    private  String email;
    @Size(min = 2, max = 50, message = "The phone number must be between 2 and 50 characters")
    @NotBlank(message = "The phone number is required")
    private  String phoneNumber;
    @Size(min = 5, max = 50, message = "The password must be between 5 and 50 characters")
    @NotBlank(message = "The user password is required")
    private String password;
    private String resetPasswordToken;
    private List<Long> roles = Collections.emptyList();
    private List<CartItemDTO> cartItems = Collections.emptyList();

    @Override
    public String toString() {
        String ans =  "userDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roles=";
        for ( Long id : roles ) ans += id + " ";
        ans += " }";
        return ans;
    }

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String email, String phoneNumber, String password, List<Long> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roles = roles;
    }

    public UserDTO(Long id, String firstName, String lastName, Date createdAt, String email, String phoneNumber, String password, List<Long> roles, String resetPasswordToken) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roles = roles;
        this.resetPasswordToken = resetPasswordToken;
    }

    // ===========================
    //   getters and setters    //
    // ===========================


    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }
}
