package com.code.challenge.authentication.runner;

import com.code.challenge.authentication.entity.Customer;
import com.code.challenge.authentication.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Slf4j
@ConditionalOnProperty(name = "app.fixtures.enabled", havingValue = "true")
@Component
public class FixturesLoader implements ApplicationRunner {

    private final CustomerRepository customerRepository;

    @Autowired
    public FixturesLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void run(ApplicationArguments args) {
        insertCustomer();
    }

    private void insertCustomer() {
        log.debug("inserting customer fixture");

        String password = BCrypt.hashpw(RandomStringUtils.random(5), BCrypt.gensalt());

        var customer = Customer.builder()
                .email("my@mail.com")
                .firstName("first name")
                .lastName("last name")
                .password(password)
                .isEnabled(true)
                .resetPasswordToken(null)
                .build();

        customerRepository.save(customer);

        log.debug("inserted customer fixture");
    }
}