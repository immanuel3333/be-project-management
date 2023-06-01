package com.group3.projectmanagementapi.status;

import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group3.projectmanagementapi.projectstatus.dto.ProjectStatus;
import com.group3.projectmanagementapi.status.dto.StatusResponse;

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
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @OneToMany(mappedBy = "status")
    @Cascade(CascadeType.ALL)
    @JsonIgnore
    private List<ProjectStatus> projectStatus;

    public StatusResponse convertToResponse() {
        return StatusResponse.builder().id(id).status(status).build();
    }

}
