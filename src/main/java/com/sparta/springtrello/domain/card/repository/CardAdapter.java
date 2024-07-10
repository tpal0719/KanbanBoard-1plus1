package com.sparta.springtrello.domain.card.repository;

import com.sparta.springtrello.domain.card.entity.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CardAdapter {
    private final CardRepository cardRepository;

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
}
