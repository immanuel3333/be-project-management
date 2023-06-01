package com.group3.projectmanagementapi.card.model.dto;

import java.sql.Timestamp;

import com.group3.projectmanagementapi.project.dto.ProjectResponse;
import com.group3.projectmanagementapi.status.dto.StatusResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardResponse {
    private Long id;
    private String title;
    private String description;
    private ProjectResponse project;
    private StatusResponse status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
