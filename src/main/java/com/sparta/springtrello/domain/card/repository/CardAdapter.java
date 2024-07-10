package com.sparta.springtrello.domain.card.repository;

import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.entity.CardUser;
import com.sparta.springtrello.exception.custom.card.CardException;
import com.sparta.springtrello.common.ResponseCodeEnum;
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
        return cardRepository.findById(cardId).orElseThrow(() -> new CardException(ResponseCodeEnum.CARD_NOT_FOUND));
    }

    public CardUser saveCardUser(CardUser cardUser) {
        return cardUserRepository.save(cardUser);
    }

    public void saveAll(List<Card> cards) {
        cardRepository.saveAll(cards);
    }

    public void delete(Card card) {
        cardRepository.delete(card);
    }
}
