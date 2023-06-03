package com.group3.projectmanagementapi.projectstatus;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.group3.projectmanagementapi.project.ProjectRepository;
import com.group3.projectmanagementapi.project.exception.ProjectNotFoundException;
import com.group3.projectmanagementapi.project.model.Project;
import com.group3.projectmanagementapi.projectstatus.model.ProjectStatus;
import com.group3.projectmanagementapi.projectstatus.model.dto.ProjectStatusRequest;
import com.group3.projectmanagementapi.status.StatusRepository;
import com.group3.projectmanagementapi.status.StatusService;
import com.group3.projectmanagementapi.status.model.Status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class ProjectStatusService {

        private final ProjectStatusRepository projectStatusRepository;

        private final ProjectRepository projectRepository;

        private final StatusRepository statusRepository;

        private final StatusService statusService;

        public ProjectStatus createNewStatus(Long projectId, ProjectStatusRequest projectStatusRequest) {
                Status status = this.statusRepository.findByStatus(projectStatusRequest.getName())
                                .orElseGet(() -> this.statusService.create(projectStatusRequest.getName()));

                Project project = this.projectRepository.findById(projectId)
                                .orElseThrow(() -> new ProjectNotFoundException(
                                                "Project with id " + projectId + " not found"));

                ProjectStatus newProjectStatus = ProjectStatus.builder()
                                .project(project)
                                .status(status)
                                .build();

                return this.projectStatusRepository.save(newProjectStatus);
        }

        public List<ProjectStatus> createDefaultProjectStatus(Project project) {
                List<Status> defaultStatusList = statusService.getDefaultStatus();

                ProjectStatus projectStatusTodo = ProjectStatus.builder().project(project)
                                .status(defaultStatusList.get(0))
                                .build();
                ProjectStatus projectStatusInDev = ProjectStatus.builder().project(project)
                                .status(defaultStatusList.get(1))
                                .build();
                ProjectStatus projectStatusDone = ProjectStatus.builder().project(project)
                                .status(defaultStatusList.get(2))
                                .build();

                List<ProjectStatus> createdProjectStatusList = projectStatusRepository
                                .saveAll(Arrays.asList(projectStatusTodo, projectStatusInDev, projectStatusDone));

                return createdProjectStatusList;
        }

        public List<ProjectStatus> getAllProjectStatus(Long projectId) {
                return projectStatusRepository.findByProjectId(projectId);
        }

}
