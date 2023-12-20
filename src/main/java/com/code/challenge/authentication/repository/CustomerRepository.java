package com.code.challenge.authentication.repository;

import com.code.challenge.authentication.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findCustomerByEmail(String email);

    Customer findCustomerByIsEnabledFalseAndResetPasswordToken(String resetPasswordToken);
}