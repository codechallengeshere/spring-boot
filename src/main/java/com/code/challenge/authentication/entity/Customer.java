package com.code.challenge.authentication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "customers",
        indexes = {
                @Index(
                        name = "index__email",
                        columnList = "email",
                        unique = true
                ),
                @Index(
                        name = "index__is_enabled",
                        columnList = "isEnabled"
                ),
                @Index(
                        name = "index__is_enabled__reset_password_token",
                        columnList = "isEnabled,resetPasswordToken"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_constraint__email",
                        columnNames = {
                                "email"
                        }
                )
        }
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(max = 50)
    @Email
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @NotBlank
    @Length(max = 50)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank
    @Length(max = 50)
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @Column(name = "reset_password_token", unique = true, length = 255)
    private String resetPasswordToken;
}
