package com.lostlink.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;


    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    private String email;


    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must contain minimum 6 characters")
    private String password;


    @NotBlank(message = "Phone number is required")
    private String phone;
}