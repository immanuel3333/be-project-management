package com.group3.projectmanagementapi.projectstatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group3.projectmanagementapi.projectstatus.model.ProjectStatus;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {

    List<ProjectStatus> findByProjectId(Long projectId);

}