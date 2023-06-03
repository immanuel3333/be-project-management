package com.group3.projectmanagementapi.project.model.dto;

import java.util.List;

import com.group3.projectmanagementapi.projectstatus.model.dto.ProjectStatusDetailedResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailedResponse {
    private long id;
    private String title;

    private List<ProjectStatusDetailedResponse> projectStatuses;
}
