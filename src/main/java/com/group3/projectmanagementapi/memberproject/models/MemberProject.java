package com.group3.projectmanagementapi.memberproject.models;

import com.group3.projectmanagementapi.customeruser.model.Customeruser;
import com.group3.projectmanagementapi.memberproject.models.dto.MemberProjectResponse;
import com.group3.projectmanagementapi.project.model.Project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "customeruser_id")
    private Customeruser customeruser;

    public MemberProjectResponse convertToResponse() {
        return MemberProjectResponse.builder()
                .id(id)
                .project(project.convertToResponse())
                .customeruser(customeruser.convertToResponse())
                .build();

    }
}
