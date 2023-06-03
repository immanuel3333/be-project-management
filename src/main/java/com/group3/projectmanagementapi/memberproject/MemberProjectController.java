package com.group3.projectmanagementapi.memberproject;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group3.projectmanagementapi.customeruser.model.Customeruser;
import com.group3.projectmanagementapi.memberproject.models.MemberProject;
import com.group3.projectmanagementapi.memberproject.models.dto.MemberProjectRequest;
import com.group3.projectmanagementapi.memberproject.models.dto.MemberProjectResponse;
import com.group3.projectmanagementapi.project.model.Project;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberProjectController {

    private final MemberProjectService memberProjectService;

    @PostMapping("/members-project")
    public ResponseEntity<MemberProjectResponse> create(@Valid @RequestBody MemberProjectRequest memberProjectRequest) {

        Project project = new Project();
        project.setId(memberProjectRequest.getIdProject());

        Customeruser customeruser = new Customeruser();
        customeruser.setId(memberProjectRequest.getIdCustomeruser());

        MemberProject newMemberProject = new MemberProject();
        newMemberProject.setProject(project);
        newMemberProject.setCustomeruser(customeruser);

        MemberProject savedMemberProject = memberProjectService.create(newMemberProject);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMemberProject.convertToResponse());

    }

    @GetMapping("/projects/{idProject}/members-project")
    public ResponseEntity<List<MemberProjectResponse>> getAllByProject(@PathVariable("idProject") Long idProject) {
        return ResponseEntity.ok(this.memberProjectService.getAllByProject(idProject).stream()
                .map(memberProject -> memberProject.convertToResponse()).toList());
    }

}
