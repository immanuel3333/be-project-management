package com.group3.projectmanagementapi.card;

import org.springframework.stereotype.Service;

import com.group3.projectmanagementapi.card.exception.CardNotFoundException;
import com.group3.projectmanagementapi.card.model.Card;
import com.group3.projectmanagementapi.project.ProjectRepository;
import com.group3.projectmanagementapi.project.exception.ProjectNotFoundException;
import com.group3.projectmanagementapi.project.model.Project;
import com.group3.projectmanagementapi.status.StatusRepository;
import com.group3.projectmanagementapi.status.exception.StatusNotFoundException;
import com.group3.projectmanagementapi.status.model.Status;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CardService {
    private final CardRepository cardRepository;
    private final StatusRepository statusRepository;
    private final ProjectRepository projectRepository;

    public Card createCard(Card card) {
        Status status = statusRepository.findById(card.getStatus().getId())
                .orElseThrow(() -> new StatusNotFoundException(
                        "Status Not Found with id " + card.getStatus().getId() + " not found"));

        Project project = projectRepository.findById(card.getProject().getId())
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project Not Found with id " + card.getProject().getId() + " not found"));

        card.setProject(project);
        card.setStatus(status);
        return cardRepository.save(card);

    }

    public Card updateCard(Card card) {
        Card existingCard = findOneByIdAndProjectId(card.getId(), card.getProject().getId());
        card.setId(existingCard.getId());
        Status status = statusRepository.findById(card.getStatus().getId())
                .orElseThrow(() -> new StatusNotFoundException(
                        "Status Not Found with id " + card.getStatus().getId() + " not found"));

        Project project = projectRepository.findById(card.getProject().getId())
                .orElseThrow(() -> new ProjectNotFoundException(
                        "Project Not Found with id " + card.getProject().getId() + "not found"));
        card.setProject(project);
        card.setStatus(status);
        Card updatedCard = this.cardRepository.save(card);
        return updatedCard;
    }

    private Card findOneByIdAndProjectId(Long cardId, Long projectId) {
        Card existingCard = cardRepository.findByIdAndProjectId(cardId, projectId)
                .orElseThrow(() -> new CardNotFoundException(
                        "Card Not Found with id " + cardId + "or porject id " + projectId + " not found"));
        return existingCard;
    }

    public void deleteCard(Long cardId, Long projectId) {
        Card existingCard = findOneByIdAndProjectId(cardId, projectId);
        cardRepository.delete(existingCard);
    }

}
