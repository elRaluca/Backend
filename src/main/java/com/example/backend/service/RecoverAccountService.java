package com.example.backend.service;

import com.example.backend.entity.OurUsers;
import com.example.backend.entity.RecoverAccount;
import com.example.backend.repository.OurUserRepo;
import com.example.backend.repository.RecoverAccountRepo;
import com.example.backend.util.ResetCodePasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RecoverAccountService {

    @Autowired
    RecoverAccountRepo recoverAccountRepo;

    @Autowired
    OurUserRepo ourUserRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;




    public void resetPasswordRequest(String userEmail) {
        Optional<OurUsers> userOp = ourUserRepo.findByEmail(userEmail);
        if (userOp.isPresent()) {
            OurUsers user = userOp.get();
            // Verificăm dacă există deja o înregistrare de recuperare pentru acest utilizator
            Optional<RecoverAccount> existingRecoverAccountOpt = recoverAccountRepo.findByUserEmail(userEmail);

            if (existingRecoverAccountOpt.isPresent()) {
                RecoverAccount existingRecoverAccount = existingRecoverAccountOpt.get();
                // Verificăm dacă contul este blocat
                if (existingRecoverAccount.getLockTime() != null && existingRecoverAccount.getLockTime().isAfter(LocalDateTime.now())) {
                    throw new RuntimeException("Contul este temporar blocat. Vă rugăm să încercați mai târziu.");
                }
            }

            // Dacă nu este blocat, sau dacă nu există o înregistrare de recuperare, creăm una nouă
            RecoverAccount recoverAccount = new RecoverAccount();
            recoverAccount.setUser(user);
            recoverAccount.setExpirationTime(LocalDateTime.now().plusMinutes(15));
            String resetCode = ResetCodePasswordUtil.generateResetCode();
            recoverAccount.setResetCode(resetCode);
            recoverAccount.setFailedAttempts(0);  // Resetăm numărul de încercări greșite
            recoverAccount.setLockTime(null);  // Resetăm timpul de blocare
            recoverAccountRepo.save(recoverAccount);

            emailService.sendChangePasswordEmail(userEmail, resetCode);
        } else {
            throw new RuntimeException("Nu s-a găsit niciun utilizator cu adresa de email " + userEmail);
        }
    }



    public boolean validateResetCode(String email, String resetCode) {
        Optional<OurUsers> user = ourUserRepo.findByEmail(email);
        if (user.isPresent()) {
            Optional<RecoverAccount> recoverAccountOpt = recoverAccountRepo.findByResetCodeAndUserId(resetCode, user.get().getId());
            if (recoverAccountOpt.isPresent()) {
                RecoverAccount recoverAccount = recoverAccountOpt.get();
                LocalDateTime currentTime = LocalDateTime.now();

                // Verifică dacă codul de resetare a expirat
                if (recoverAccount.getExpirationTime() != null && recoverAccount.getExpirationTime().isBefore(currentTime)) {
                    return false;
                }

                // Verifică dacă codul este corect
                if (recoverAccount.getResetCode() != null && recoverAccount.getResetCode().equals(resetCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateAndProcessResetCode(String email, String resetCode) {
        Optional<RecoverAccount> recoverAccountOpt = recoverAccountRepo.findByUserEmail(email);
        if (recoverAccountOpt.isPresent()) {
            RecoverAccount recoverAccount = recoverAccountOpt.get();
            LocalDateTime currentTime = LocalDateTime.now();
            if (recoverAccount.getLockTime() != null && recoverAccount.getLockTime().isAfter(currentTime)) {
                throw new RuntimeException("Contul este blocat temporar. Vă rugăm să încercați mai târziu.");
            }
            boolean isValid = validateResetCode(email, resetCode);
            if (!isValid) {
                recoverAccount.setFailedAttempts(recoverAccount.getFailedAttempts() + 1);
                if (recoverAccount.getFailedAttempts() >= 3) {
                    recoverAccount.setLockTime(currentTime.plusMinutes(3));
                }
                recoverAccountRepo.save(recoverAccount);
            } else {
                recoverAccount.setFailedAttempts(0);
                recoverAccount.setLockTime(null);
                recoverAccountRepo.save(recoverAccount);
            }
            return isValid;
        }
        throw new RuntimeException("Nu s-a găsit niciun utilizator cu adresa de email " + email);
    }


    public void changeUserPassword(String userEmail, String newPassword) {
        Optional<OurUsers> optionalUser = ourUserRepo.findByEmail(userEmail);

        if (optionalUser.isPresent()) {
            OurUsers user = optionalUser.get();
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            ourUserRepo.save(user);
        } else {
            throw new RuntimeException("Nu s-a găsit niciun utilizator cu adresa de email " + userEmail);
        }
    }



}
