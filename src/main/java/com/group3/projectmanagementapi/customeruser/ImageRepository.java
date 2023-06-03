package com.group3.projectmanagementapi.customeruser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group3.projectmanagementapi.customeruser.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    public Optional<Image> findById(Long id);

}
