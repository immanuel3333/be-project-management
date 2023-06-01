package com.group3.projectmanagementapi.project;

import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group3.projectmanagementapi.memberproject.dto.MemberProject;
import com.group3.projectmanagementapi.project.dto.ProjectResponse;
import com.group3.projectmanagementapi.projectstatus.dto.ProjectStatus;

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
}
