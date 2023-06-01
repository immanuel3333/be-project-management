package com.group3.projectmanagementapi.card.model;

import java.sql.Timestamp;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group3.projectmanagementapi.card.model.dto.CardResponse;
import com.group3.projectmanagementapi.project.Project;
import com.group3.projectmanagementapi.status.Status;

import jakarta.persistence.Column;
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
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @Cascade(value = CascadeType.ALL)
    @JoinColumn(name = "status_id")
    @JsonIgnore
    private Status status;

    @ManyToOne
    @Cascade(value = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public CardResponse convertToResponse() {
        return CardResponse.builder().id(id).title(title).description(description).status(status.convertToResponse())
                .project(project.convertToResponse()).createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
