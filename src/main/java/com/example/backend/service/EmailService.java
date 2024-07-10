package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String to, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Verificarea Contului");
            message.setText("Codul tău de verificare este: " + otp);
            javaMailSender.send(message);
        } catch (Exception e) {
            // Loghează excepția pentru depanare
            e.printStackTrace();
        }
    }


    public void sendChangePasswordEmail(String to, String resetCode) {
        try{
        SimpleMailMessage messageChangePassword = new SimpleMailMessage();
        messageChangePassword.setTo(to);
        messageChangePassword.setSubject("Recupreaza Contul");
        messageChangePassword.setText("Codul de resetare a parolei este" + resetCode +
                ".Te rugam foloseste ascest cod in platforma noastra");
        javaMailSender.send(messageChangePassword);
    } catch(
    Exception e)

    {
        e.printStackTrace();
    }
}




}
