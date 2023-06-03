package com.group3.projectmanagementapi.memberproject;

import java.util.List;

import org.springframework.stereotype.Service;

import com.group3.projectmanagementapi.customeruser.CustomeruserService;
import com.group3.projectmanagementapi.customeruser.model.Customeruser;
import com.group3.projectmanagementapi.memberproject.models.MemberProject;
import com.group3.projectmanagementapi.project.ProjectService;
import com.group3.projectmanagementapi.project.model.Project;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberProjectService {
    private final MemberProjectRepository memberProjectRepository;
    private final ProjectService projectService;
    private final CustomeruserService customeruserService;

    public List<MemberProject> getAll() {
        return memberProjectRepository.findAll();
    }

    public List<MemberProject> getAllByProject(Long idProject) {
        return memberProjectRepository.findByProjectId(idProject);
    }

    public List<MemberProject> getCustomerUserDetails() {
        return memberProjectRepository.getCustomerUserDetails();
    }

    public MemberProject create(MemberProject memberProject) {

        Project project = projectService.findOneById(memberProject.getProject().getId());
        Customeruser customeruser = customeruserService.findOneById(memberProject.getCustomeruser().getId());

        memberProject.setProject(project);
        memberProject.setCustomeruser(customeruser);

        return memberProjectRepository.save(memberProject);
    }
}
