package com.group3.projectmanagementapi.customeruser.model.dto;

import com.group3.projectmanagementapi.applicationuser.ApplicationUser;
import com.group3.projectmanagementapi.customeruser.model.Customeruser;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomeruserRegisterRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    public Customeruser convertToEntity() {
        Customeruser customer = Customeruser.builder()
                .username(username)
                .name(name)
                .email(email)
                .build();

        ApplicationUser applicationUser = ApplicationUser.builder()
                .name(name)
                .username(username)
                .email(email)
                .password(password)
                .build();

        customer.setApplicationUser(applicationUser);
        applicationUser.setCustomer(customer);

        return customer;
    }
}
