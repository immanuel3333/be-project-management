package com.group3.projectmanagementapi.customeruser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.group3.projectmanagementapi.customeruser.model.Customeruser;

public interface CustomeruserRepository extends JpaRepository<Customeruser, Long> {
    Customeruser findByUsernameOrEmail(String username, String email);

    Customeruser findByUsername(String username);

    @Query("SELECT c FROM Customeruser c WHERE NOT EXISTS (SELECT 1 FROM MemberProject mp WHERE mp.project.id = ?1 AND mp.customeruser.id = c.id) AND email = ?2")
    List<Customeruser> findUnassignedUsers(Long projectId, String email);
}
