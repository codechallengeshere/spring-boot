package com.code.challenge.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderConfig {

    private final MailPropertiesConfig mailPropertiesConfig;

    @Autowired
    public MailSenderConfig(MailPropertiesConfig mailPropertiesConfig) {
        this.mailPropertiesConfig = mailPropertiesConfig;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        var javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailPropertiesConfig.getHost());
        javaMailSender.setPort(mailPropertiesConfig.getPort());

        javaMailSender.setUsername(mailPropertiesConfig.getUsername());
        javaMailSender.setPassword(mailPropertiesConfig.getPassword());

        var javaMailProperties = javaMailSender.getJavaMailProperties();
        javaMailProperties.put("mail.transport.protocol", mailPropertiesConfig.getTransportProtocol());
        javaMailProperties.put("mail.smtp.auth", mailPropertiesConfig.isSmtpAut());
        javaMailProperties.put("mail.smtp.starttls.enable", mailPropertiesConfig.isSmtpStartTlsEnable());
        javaMailProperties.put("mail.debug", mailPropertiesConfig.isDebug());

        return javaMailSender;
    }
}
