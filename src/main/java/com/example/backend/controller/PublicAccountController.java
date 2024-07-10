package com.example.backend.controller;


import com.example.backend.dto.OtpVerificationRequest;
import com.example.backend.dto.ReqRes;
import com.example.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
public class  PublicAccountController {

    @Autowired
    private AuthService authService;



    @PostMapping("/singup")
    public ResponseEntity<ReqRes>singUp(@Valid @RequestBody ReqRes singUpRequest){
        return ResponseEntity.ok(authService.signUp(singUpRequest));
    }

    @PostMapping("/singin")
    public ResponseEntity<ReqRes> singIn(@RequestBody ReqRes signInRequest) {
        ReqRes response = authService.singIn(signInRequest);
        HttpStatus status = HttpStatus.resolve(response.getStatusCode());
        return ResponseEntity.status(status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    @PostMapping("/refresh")
    public ResponseEntity<ReqRes>refreshToken(@RequestBody ReqRes refreshTokenRequest ){
        return  ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerificationRequest request) {
        boolean isVerified = authService.verifyOtp(request.getEmail(), request.getOtp());
        if (isVerified) {
            return ResponseEntity.ok("Contul a fost verificat cu succes.");
        } else {
            return ResponseEntity.badRequest().body("Verificarea OTP a eșuat.");
        }
    }
    @PostMapping("/resend-otp")
    public ResponseEntity<ReqRes> resendOtp(@RequestParam("email") String email) {
        ReqRes response = authService.resendOtp(email);
        HttpStatus status = HttpStatus.resolve(response.getStatusCode());
        return ResponseEntity.status(status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }




}
