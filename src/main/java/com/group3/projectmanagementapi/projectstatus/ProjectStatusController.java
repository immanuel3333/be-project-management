package com.group3.projectmanagementapi.projectstatus;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group3.projectmanagementapi.projectstatus.model.ProjectStatus;
import com.group3.projectmanagementapi.projectstatus.model.dto.ProjectStatusRequest;
import com.group3.projectmanagementapi.projectstatus.model.dto.ProjectStatusResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProjectStatusController {
    private final ProjectStatusService projectStatusService;

    @PostMapping("/projects/{idProject}/status")
    public ResponseEntity<ProjectStatusResponse> create(@PathVariable("idProject") Long projectId,
            @Valid @RequestBody ProjectStatusRequest projectStatusRequest) {

        ProjectStatus newProject = projectStatusService.createNewStatus(projectId, projectStatusRequest);
        ProjectStatusResponse projectStatusResponse = newProject.convertToResponse();
        return ResponseEntity.status(HttpStatus.CREATED).body(projectStatusResponse);
    }

    @GetMapping("/projects/{idProject}/status")
    public ResponseEntity<List<ProjectStatusResponse>> getAllStatus(@PathVariable("idProject") Long projectId) {
        List<ProjectStatus> projectStatuses = this.projectStatusService.getAllProjectStatus(projectId);
        return ResponseEntity
                .ok(projectStatuses.stream().map(projectStatus -> projectStatus.convertToResponse()).toList());
    }
}
