package com.group3.projectmanagementapi.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.group3.projectmanagementapi.customeruser.CustomeruserRepository;
import com.group3.projectmanagementapi.customeruser.exception.CustomeruserNotFoundException;
import com.group3.projectmanagementapi.customeruser.model.Customeruser;
import com.group3.projectmanagementapi.memberproject.MemberProjectRepository;
import com.group3.projectmanagementapi.memberproject.models.MemberProject;
import com.group3.projectmanagementapi.project.exception.ProjectNotFoundException;
import com.group3.projectmanagementapi.project.model.Project;
import com.group3.projectmanagementapi.projectstatus.ProjectStatusRepository;
import com.group3.projectmanagementapi.status.StatusRepository;
import com.group3.projectmanagementapi.status.StatusService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final StatusRepository statusRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final StatusService statusService;
    private final CustomeruserRepository customeruserRepository;
    private final MemberProjectRepository memberProjectRepository;

    public Project create(Project newProject, Long id) {
        Customeruser customeruser = customeruserRepository.findById(id)
                .orElseThrow(() -> new CustomeruserNotFoundException("Customer user with ID " + id + " not found"));
        Project savedProject = projectRepository.save(newProject);

        MemberProject memberProject = MemberProject.builder()
                .project(savedProject)
                .customeruser(customeruser)
                .build();

        memberProjectRepository.save(memberProject);
        return savedProject;
    }

    public Page<Project> getAll(Long id, Pageable pageable) {
        return projectRepository.findByMemberProjectsCustomeruserId(id, pageable);
    }

    public Project findOneById(long id) {
        Project existingProject = this.projectRepository.findById(id).get();
        if (existingProject == null) {
            throw new ProjectNotFoundException("Project with id " + id + " not found");
        }
        return existingProject;
    }
}
