package com.group3.projectmanagementapi.project;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group3.projectmanagementapi.project.model.Project;
import com.group3.projectmanagementapi.project.model.dto.ProjectDetailedResponse;
import com.group3.projectmanagementapi.project.model.dto.ProjectRequest;
import com.group3.projectmanagementapi.project.model.dto.ProjectResponse;
import com.group3.projectmanagementapi.projectstatus.ProjectStatusService;
import com.group3.projectmanagementapi.projectstatus.model.ProjectStatus;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    private final ProjectStatusService projectStatusService;

    @PostMapping("customerusers/{customerId}/projects")
    public ResponseEntity<ProjectResponse> create(@PathVariable("customerId") Long customerId,
            @Valid @RequestBody ProjectRequest projectRequest) {
        Project newProject = projectRequest.convertToEntity();
        Project saveProject = this.projectService.create(newProject, customerId);
        List<ProjectStatus> projectStatuses = this.projectStatusService.createDefaultProjectStatus(saveProject);
        saveProject.setProjectStatus(projectStatuses);
        ProjectResponse projectResponse = saveProject.convertToResponse();
        return ResponseEntity.status(HttpStatus.CREATED).body(projectResponse);
    }

    @GetMapping("customerusers/{customerId}/projects")
    public ResponseEntity<Page<ProjectResponse>> getAll(@PathVariable("customerId") Long customerId,
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Project> projectPage = this.projectService.getAll(customerId, pageable);

        List<ProjectResponse> projectResponses = projectPage.getContent().stream()
                .map(Project::convertToResponse)
                .toList();

        return ResponseEntity.ok(new PageImpl<>(projectResponses, pageable, projectPage.getTotalElements()));
    }

    @GetMapping("/projects/{idProject}")
    public ResponseEntity<ProjectDetailedResponse> getOne(@PathVariable("idProject") long idProject) {
        Project existingProject = this.projectService.findOneById(idProject);
        return ResponseEntity.ok(existingProject.convertToDetailedResponse());
    }
}
