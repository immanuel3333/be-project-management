package com.group3.projectmanagementapi.customeruser;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group3.projectmanagementapi.customeruser.model.Customeruser;

public interface CustomeruserRepository extends JpaRepository<Customeruser, Long> {
    Customeruser findByUsernameOrEmail(String username, String email);

    Customeruser findByUsername(String username);
}
