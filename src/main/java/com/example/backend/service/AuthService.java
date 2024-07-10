package com.example.backend.service;

import com.example.backend.dto.ReqRes;
import com.example.backend.entity.OurUsers;
import com.example.backend.config.SecurityConfig;
import com.example.backend.repository.OurUserRepo;
import com.example.backend.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private OurUserRepo ourUserRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    private boolean isPasswordComplexEnough(String password) {
        if (password == null) {
            return false;
        }
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;

            if (hasUpper && hasLower && hasDigit && hasSpecial) {
                return true;
            }
        }
        return password.length() >= 8 && hasUpper && hasLower && hasDigit && hasSpecial;
    }


    public static void main(String[] args) {
        PasswordComplexityTester tester = new PasswordComplexityTester();
        tester.testPasswordComplexity();
    }

    private static class PasswordComplexityTester {
        void testPasswordComplexity() {
            AuthService authService = new AuthService();
            assertTest(authService.isPasswordComplexEnough("Password123!"), true, "Test 1 - Complex enough");
            assertTest(authService.isPasswordComplexEnough("password"), false, "Test 2 - No upper, digits, or special");
            assertTest(authService.isPasswordComplexEnough("PASSWORD123"), false, "Test 3 - No lower or special");
            assertTest(authService.isPasswordComplexEnough("pass"), false, "Test 4 - Short password");
            assertTest(authService.isPasswordComplexEnough("12345678"), false, "Test 5 - Digits only");
            assertTest(authService.isPasswordComplexEnough("Pass.1234"), true, "Test 6 - Minimum length and all conditions met");
            assertTest(authService.isPasswordComplexEnough(""), false, "Test 7 - Empty string");
            assertTest(authService.isPasswordComplexEnough(null), false, "Test 8 - Null input");
        }

        void assertTest(boolean result, boolean expected, String testName) {
            if (result == expected) {
                System.out.println(testName + ": PASS");
            } else {
                System.out.println(testName + ": FAIL");
            }
        }
    }

    public ReqRes signUp(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        String password = registrationRequest.getPassword();
        if (!isPasswordComplexEnough(password)) {
            resp.setMessage("Password does not meet " +
                    "complexity requirements.");
            resp.setStatusCode(400);
            return resp;
        }
        try {
            Optional<OurUsers> existingUser = ourUserRepo.findByEmail(registrationRequest.getEmail());
            if (existingUser.isPresent()) {

                resp.setMessage("Email already in use.");
                resp.setStatusCode(400);
                return resp;
            }
            OurUsers ourUsers = new OurUsers();
            ourUsers.setEmail(registrationRequest.getEmail());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUsers.setName(registrationRequest.getName());
            ourUsers.setRole("ROLE_USER");
            String otp = OtpUtil.generateOtp();
            ourUsers.setOtp(otp);
            ourUsers.setOtpGeneratedTime(LocalDateTime.now());
            ourUsers.setActive(false);

            OurUsers ourUserResult=ourUserRepo.save(ourUsers);
            if (ourUserResult != null && ourUserResult.getId() > 0) {
                emailService.sendOtpEmail(ourUserResult.getEmail(), ourUsers.getOtp());
                resp.setOurUsers(ourUserResult);
                resp.setMessage("User saved successfully. Please verify your email.");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;

    }
    public ReqRes singIn(ReqRes signInRequest) {
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

            OurUsers user = ourUserRepo.findByEmail(signInRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (!user.isActive()) {
                response.setStatusCode(HttpStatus.FORBIDDEN.value());
                response.setError("Account is not activated. Please check your email for the activation link.");
                return response;
            }

            String jwt = jwtUtils.generateToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully Signed In");
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setRole(user.getRole());
        } catch (DisabledException e) {
            response.setStatusCode(HttpStatus.FORBIDDEN.value());
            response.setError("Account is not activated. Please check your email for the activation link.");
        } catch (BadCredentialsException e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            response.setError("Bad credentials");

    } catch (UsernameNotFoundException e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setError("User not found");
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setError("An unexpected error occurred");
        }

        return response;
    }


    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response= new ReqRes();
        String ourEmail=jwtUtils.extractUsername(refreshTokenReqiest.getToken());
        OurUsers users=ourUserRepo.findByEmail(ourEmail).orElseThrow();
        if(jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)){
            var jwt=jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refresh Token");
        }
        response.setStatusCode(500);
        return response;

    }

    public boolean verifyOtp(String email, String otp) {
        OurUsers user = ourUserRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizatorul nu a fost găsit"));
        if (user.getOtp() != null && user.getOtp().equals(otp)) {
            LocalDateTime otpGenerationTime = user.getOtpGeneratedTime();
            if (user.getOtpGeneratedTime() != null && user.getOtpGeneratedTime().plusMinutes(2).
                    isAfter(LocalDateTime.now())) {
                user.setActive(true);
                user.setOtp(null);
                user.setOtpGeneratedTime(null);
                ourUserRepo.save(user);
                return true;
            }
                else {
                    String newOtp = OtpUtil.generateOtp();
                    user.setOtp(newOtp);
                    user.setOtpGeneratedTime(LocalDateTime.now());
                    ourUserRepo.save(user);
                    emailService.sendOtpEmail(user.getEmail(), newOtp);

                    return false;
                }

            } else {
            return false;
        }
    }

    public ReqRes resendOtp(String email) {
        ReqRes response = new ReqRes();
        try {
            OurUsers user = ourUserRepo.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilizatorul nu a fost găsit"));

            // Verificăm dacă utilizatorul este deja activ
            if (user.isActive()) {
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Contul este deja activ.");
                return response;
            }

            // Verificăm dacă a trecut suficient timp de la ultima generare a OTP-ului
            LocalDateTime lastOtpGenerationTime = user.getOtpGeneratedTime();
            if (lastOtpGenerationTime != null && lastOtpGenerationTime.plusMinutes(1).isAfter(LocalDateTime.now())) {
                response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setMessage("A fost deja trimis un OTP în ultimele 1 minute. Vă rugăm să așteptați.");
                return response;
            }

            // Generăm un nou OTP și actualizăm detaliile utilizatorului
            String newOtp = OtpUtil.generateOtp();
            user.setOtp(newOtp);
            user.setOtpGeneratedTime(LocalDateTime.now());
            ourUserRepo.save(user);

            // Trimitem e-mailul cu noul OTP
            emailService.sendOtpEmail(user.getEmail(), newOtp);

            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Un nou OTP a fost trimis la adresa de e-mail.");
        } catch (UsernameNotFoundException e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setError("Utilizatorul nu a fost găsit.");
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setError("A apărut o eroare neașteptată.");
        }
        return response;
    }


}

