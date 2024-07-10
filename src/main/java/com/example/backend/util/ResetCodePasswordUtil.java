package com.example.backend.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Component
public class ResetCodePasswordUtil {

        private static final SecureRandom secureRandom = new SecureRandom();
        private static final int OTP_LENGTH = 6;

        public static String generateResetCode() {
            StringBuilder resetCode = new StringBuilder(OTP_LENGTH);
            for (int i = 0; i < OTP_LENGTH; i++) {
                resetCode.append(secureRandom.nextInt(10));
            }
            return resetCode.toString();
        }
}
