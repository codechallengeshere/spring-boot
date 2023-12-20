package com.code.challenge.authentication.config;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@Component
@ConfigurationProperties(prefix = "app.mail")
public class MailPropertiesConfig {

    @NotEmpty
    String from;

    @NotNull
    Integer port;

    @NotEmpty
    String host;

    @NotEmpty
    String username;

    @NotEmpty
    String password;

    @NotEmpty
    @Value("${app.mail.transport.protocol}")
    String transportProtocol;

    @Value("${app.mail.transport.smtp.auth}")
    boolean smtpAut;

    @Value("${app.mail.transport.smtp.starttls.enable}")
    boolean smtpStartTlsEnable;

    boolean debug;
}
