package com.example.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OtpVerificationRequest {
    @Email(message = "Adresa de email nu este validă.")
    @NotEmpty(message = "Adresa de email nu poate fi goală.")
    private String email;

    @NotEmpty(message = "OTP-ul nu poate fi gol.")
    private String otp;

    private String resetCode;

    @NotEmpty(message = "Parola nouă nu poate fi goală.")
    private String newPassword;

    private String confirmPassword;
}
