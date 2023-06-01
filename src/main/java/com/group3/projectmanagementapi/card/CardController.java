package com.group3.projectmanagementapi.card;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group3.projectmanagementapi.card.model.Card;
import com.group3.projectmanagementapi.card.model.dto.CardRequest;
import com.group3.projectmanagementapi.card.model.dto.CardResponse;
import com.group3.projectmanagementapi.project.Project;
import com.group3.projectmanagementapi.status.Status;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/project/{idProject}/cards")
    public ResponseEntity<CardResponse> createCard(@PathVariable("idProject") Long projectId,
            @Valid @RequestBody CardRequest cardRequest) {
        Card newCard = cardRequest.convertToEntity();

        Project project = new Project();
        project.setId(projectId);

        Status status = new Status();
        status.setId(cardRequest.getStatusId());

        newCard.setProject(project);
        newCard.setStatus(status);

        Card createdCard = cardService.createCard(newCard);
        CardResponse cardResponse = createdCard.convertToResponse();
        return ResponseEntity.status(HttpStatus.CREATED).body(cardResponse);
    }

    @PutMapping("/project/{idProject}/cards/{idCard}")
    public ResponseEntity<CardResponse> updateCard(@PathVariable("idProject") Long projectId,
            @PathVariable("idCard") Long cardId, @Valid @RequestBody CardRequest cardRequest) {
        Card card = cardRequest.convertToEntity();

        card.setId(cardId);
        Project project = new Project();
        project.setId(projectId);

        card.setProject(project);

        Card updatedCard = cardService.updateCard(card);
        CardResponse cardResponse = updatedCard.convertToResponse();

        return ResponseEntity.status(HttpStatus.OK).body(cardResponse);
    }

    @DeleteMapping("/project/{idProject}/cards/{idCard}")
    public ResponseEntity<Void> deleteCard(@PathVariable("idProject") Long projectId,
            @PathVariable("idCard") Long cardId) {
        cardService.deleteCard(cardId, projectId);
        return ResponseEntity.noContent().build();
    }

}
