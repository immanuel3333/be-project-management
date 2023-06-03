package com.group3.projectmanagementapi.status;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group3.projectmanagementapi.status.model.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findByStatus(String name);

}
