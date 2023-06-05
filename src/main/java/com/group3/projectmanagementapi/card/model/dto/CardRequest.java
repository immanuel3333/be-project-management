package com.group3.projectmanagementapi.card.model.dto;

import com.group3.projectmanagementapi.card.model.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {
    @NotEmpty(message = "Title is required")
    private String title;

    @NotEmpty(message = "Description is required")
    private String description;

    private Long statusId;

    public Card convertToEntity() {
        return Card.builder().title(title).description(description).build();
    }
}
