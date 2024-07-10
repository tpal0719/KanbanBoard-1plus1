package com.sparta.springtrello.domain.card.repository;

import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.entity.CardUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CardAdapter {
    private final CardRepository cardRepository;
    private final CardUserRepository cardUserRepository;

    public List<Card> findAllByBoardId(Long boardId) {
        return cardRepository.findAllByBoardId(boardId);
    }

    public List<Card> findAllByColumnId(Long columnId) {
        return cardRepository.findAllByColumnId(columnId);
    }

    public List<Card> findAllByUserId(Long userId) {
        return cardRepository.findAllByUserId(userId);
    }

    public Card save(Card card) {
        return cardRepository.save(card);
    }

    public Card findById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("Card not found"));
    }

    public CardUser saveCardUser(CardUser cardUser) {
        return cardUserRepository.save(cardUser);
    }
}
