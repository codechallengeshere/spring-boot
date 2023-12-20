package com.code.challenge.authentication.service;

import co.com.bancolombia.datamask.MaskUtils;
import com.code.challenge.authentication.config.MailPropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
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

    public void sendResetPasswordTokenMail(String to, String token) {
        var params = Map.of(
                "to", MaskUtils.maskAsEmail(to),
                "token", MaskUtils.mask(token, 21, 0)
        );
        log.debug("EmailService#sendEmail: " + params);

        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailPropertiesConfig.getFrom());
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("reset password token");
        simpleMailMessage.setText(token);

        javaMailSender.send(simpleMailMessage);

        log.debug("EmailService#sendEmail: success");
    }
}
