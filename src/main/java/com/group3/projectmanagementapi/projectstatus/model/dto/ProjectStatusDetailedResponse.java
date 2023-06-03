package com.group3.projectmanagementapi.projectstatus.model.dto;

import java.util.List;

import com.group3.projectmanagementapi.card.model.dto.CardResponseForProject;
import com.group3.projectmanagementapi.status.model.dto.StatusResponse;

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
public class ProjectStatusDetailedResponse {
    private long id;
    private StatusResponse status;
    private List<CardResponseForProject> cards;

}
