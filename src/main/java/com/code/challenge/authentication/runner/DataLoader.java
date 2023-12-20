package com.code.challenge.authentication.runner;

import com.code.challenge.authentication.entity.Customer;
import com.code.challenge.authentication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final CustomerRepository customerRepository;

    @Autowired
    public DataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void run(ApplicationArguments args) {
        var customer = Customer.builder()
                .email("my@mail.com")
                .firstName("first name")
                .lastName("last name")
                .isEnabled(true)
                .resetPasswordToken(null)
                .build();

        customerRepository.save(customer);
    }
}