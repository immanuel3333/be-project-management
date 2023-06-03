package com.group3.projectmanagementapi.project.model.dto;

import com.group3.projectmanagementapi.project.model.Project;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    @NotEmpty(message = "Title is required")
    private String title;

    public Project convertToEntity() {
        return Project.builder()
            .title(this.title)
            .build();
    }
}
