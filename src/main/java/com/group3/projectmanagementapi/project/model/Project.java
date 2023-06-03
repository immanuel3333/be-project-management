package com.group3.projectmanagementapi.project.model;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group3.projectmanagementapi.memberproject.models.MemberProject;
import com.group3.projectmanagementapi.project.model.dto.ProjectDetailedResponse;
import com.group3.projectmanagementapi.project.model.dto.ProjectResponse;
import com.group3.projectmanagementapi.projectstatus.model.ProjectStatus;
import com.group3.projectmanagementapi.projectstatus.model.dto.ProjectStatusDetailedResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "project")
    @Cascade(CascadeType.ALL)
    @JsonIgnore
    private List<ProjectStatus> projectStatus;

    @OneToMany(mappedBy = "project")
    @Cascade(CascadeType.ALL)
    @JsonIgnore
    private List<MemberProject> memberProjects;

    public ProjectResponse convertToResponse() {
        return ProjectResponse.builder().id(id).title(title).build();
    }

    public ProjectDetailedResponse convertToDetailedResponse() {
        List<ProjectStatusDetailedResponse> projectStatusResponses = projectStatus.stream()
                .map(ProjectStatus::convertToDetailedResponse)
                .collect(Collectors.toList());

        return ProjectDetailedResponse.builder()
                .id(id)
                .title(title)
                .projectStatuses(projectStatusResponses)
                .build();
    }
}
