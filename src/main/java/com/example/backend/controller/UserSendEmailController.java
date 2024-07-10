package com.example.backend.controller;

import com.example.backend.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/email")
public class UserSendEmailController {

    @Autowired
    private SendEmailService emailService;

    @PostMapping("/send")
    @ResponseBody
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text) {
        try {
            emailService.sendEmail(to, subject, text);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
}
