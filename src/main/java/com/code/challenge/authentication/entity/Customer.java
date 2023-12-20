package com.code.challenge.authentication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Validated
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "customers",
        indexes = {
                @Index(
                        name = "email__idx",
                        columnList = "email",
                        unique = true
                ),
                @Index(
                        name = "is_enabled__idx",
                        columnList = "isEnabled"
                ),
                @Index(
                        name = "is_enabled__reset_password_token__idx",
                        columnList = "isEnabled,resetPasswordToken"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "email__uc",
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

    @NotBlank
    @Length(max = 255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @NotNull
    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @Column(name = "reset_password_token", unique = true, length = 255)
    private String resetPasswordToken;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
}
