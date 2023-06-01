package com.group3.projectmanagementapi.applicationuser;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.group3.projectmanagementapi.customeruser.model.Customeruser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    private String email;

    private String username;

    private String password;

    @OneToOne
    private Customeruser customer;
}
