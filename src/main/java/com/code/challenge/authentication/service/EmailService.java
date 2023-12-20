package com.code.challenge.authentication.service;

import com.code.challenge.authentication.config.MailPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final MailPropertiesConfig mailPropertiesConfig;
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(
            MailPropertiesConfig mailPropertiesConfig,
            JavaMailSender javaMailSender
    ) {
        this.mailPropertiesConfig = mailPropertiesConfig;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailPropertiesConfig.getFrom());
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
    }
}
