package com.example.backend.controller;

import com.example.backend.dto.OtpVerificationRequest;
import com.example.backend.repository.RecoverAccountRepo;
import com.example.backend.service.RecoverAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicRecoverAccountController {

    @Autowired
    private RecoverAccountService recoverAccountService;
    @Autowired
    private RecoverAccountRepo recoverAccountRepo;


    @PostMapping("/request-reset-password")
    public ResponseEntity<?> requestResetPassword(@RequestBody OtpVerificationRequest request) {
        String email = request.getEmail();
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Adresa de email este necesară pentru resetarea parolei.");
        }

        try {
            recoverAccountService.resetPasswordRequest(email);
            return ResponseEntity.ok("Instrucțiuni de resetare a parolei au fost trimise la adresa de email furnizată.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Nu s-a putut procesa cererea: " + e.getMessage());
        }
    }


    @PostMapping("/set-password")
    public ResponseEntity<?> validateResetCode(@RequestBody OtpVerificationRequest request) {
        if (request.getResetCode() == null || request.getNewPassword() == null || request.getConfirmPassword() == null) {
            return ResponseEntity.badRequest().body("Toate câmpurile sunt obligatorii: codul de resetare, noua parolă și confirmarea parolei.");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Noua parolă și confirmarea acesteia nu coincid.");
        }

        try {
            boolean isValidResetCode = recoverAccountService.validateAndProcessResetCode(request.getEmail(), request.getResetCode());
            if (!isValidResetCode) {
                return ResponseEntity.badRequest().body("Codul de resetare este invalid, a expirat sau contul este temporar blocat.");
            }

            recoverAccountService.changeUserPassword(request.getEmail(), request.getNewPassword());
            return ResponseEntity.ok().body("Parola a fost schimbată cu succes.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("A apărut o eroare la schimbarea parolei. Vă rugăm să încercați din nou mai târziu.");
        }
    }






}








