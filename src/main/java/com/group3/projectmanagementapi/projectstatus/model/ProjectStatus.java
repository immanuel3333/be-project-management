package com.group3.projectmanagementapi.projectstatus.model;

import java.util.List;
import java.util.stream.Collectors;

import com.group3.projectmanagementapi.card.model.Card;
import com.group3.projectmanagementapi.card.model.dto.CardResponseForProject;
import com.group3.projectmanagementapi.project.model.Project;
import com.group3.projectmanagementapi.projectstatus.model.dto.ProjectStatusDetailedResponse;
import com.group3.projectmanagementapi.projectstatus.model.dto.ProjectStatusResponse;
import com.group3.projectmanagementapi.status.model.Status;
import com.group3.projectmanagementapi.status.model.dto.StatusResponse;

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
public class ProjectStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    public ProjectStatusResponse convertToResponse() {
        return ProjectStatusResponse.builder().id(this.id).status(this.status).build();
    }

    public ProjectStatusDetailedResponse convertToDetailedResponse() {
        List<CardResponseForProject> cardResponses = status.getCards().stream()
                .filter(card -> card.getProject().getId().equals(project.getId()))
                .map(Card::convertForProjectResponse)
                .collect(Collectors.toList());

        StatusResponse statusResponse = status.convertToResponse();

        return ProjectStatusDetailedResponse.builder()
                .id(id)
                .status(statusResponse)
                .cards(cardResponses)
                .build();
    }
}
