package com.group3.projectmanagementapi.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.group3.projectmanagementapi.project.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findByMemberProjectsCustomeruserId(Long id, Pageable pageable);

}
