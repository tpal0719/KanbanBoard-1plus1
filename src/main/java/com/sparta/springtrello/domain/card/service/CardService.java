package com.sparta.springtrello.domain.card.service;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.domain.card.dto.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.dto.CardResponseDto;
import com.sparta.springtrello.domain.card.dto.CardUpdateRequestDto;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.entity.CardUser;
import com.sparta.springtrello.domain.card.repository.CardAdapter;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import com.sparta.springtrello.domain.column.repository.TaskColumnAdapter;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.repository.UserAdapter;
import com.sparta.springtrello.exception.custom.common.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardAdapter cardAdapter;
    private final TaskColumnAdapter taskColumnAdapter;
    private final UserAdapter userAdapter;

    // 카드 생성
    @Transactional
    public void createCard(Long columnId, CardCreateRequestDto requestDto, Long userId) {
        TaskColumn taskColumn = taskColumnAdapter.findById(columnId);
        int cardOrder = taskColumn.getCards().size() + 1;
        User user = userAdapter.findById(userId);

        Card card = Card.builder()
                .cardName(requestDto.getCardName())
                .cardOrder(cardOrder)
                .taskColumn(taskColumn)
                .build();

        cardAdapter.save(card);

        CardUser cardUser = CardUser.builder()
                .user(user)
                .card(card)
                .isCreator(true)
                .build();

        cardAdapter.saveCardUser(cardUser);
    }

    // 카드 조회(보드별)
    @Transactional(readOnly = true)
    public List<CardResponseDto> getCardsByBoardId(Long boardId) {
        List<Card> cards = cardAdapter.findAllByBoardId(boardId);
        return CardResponseDto.fromEntities(cards);
    }

    // 카드 조회(칼럼별)
    @Transactional(readOnly = true)
    public List<CardResponseDto> getCardsByColumnId(Long columnId) {
        List<Card> cards = cardAdapter.findAllByColumnId(columnId);
        return CardResponseDto.fromEntities(cards);
    }

    // 카드 조회(유저별)
    @Transactional(readOnly = true)
    public List<CardResponseDto> getCardsByUserId(Long userId) {
        List<Card> cards = cardAdapter.findAllByUserId(userId);
        return CardResponseDto.fromEntities(cards);
    }

    // 카드 작업자 할당
    @Transactional
    public void addCardMember(Long cardId, Long userId) {
        Card card = cardAdapter.findById(cardId);
        User user = userAdapter.findById(userId);

        CardUser cardUser = CardUser.builder()
                .user(user)
                .card(card)
                .isCreator(false)
                .build();

        cardAdapter.saveCardUser(cardUser);
    }

    // 카드 상세 변경
    @Transactional
    public void updateCard(Long cardId, CardUpdateRequestDto requestDto, Long userId) {
        Card card = cardAdapter.findById(cardId);
        validateCardOwner(card, userId);

        if (requestDto.getCardName() != null) {
            card.setCardName(requestDto.getCardName());
        }
        if (requestDto.getCardDescription() != null) {
            card.setCardDescription(requestDto.getCardDescription());
        }
        if (requestDto.getDueDate() != null) {
            card.setDueDate(requestDto.getDueDate());
        }
        cardAdapter.save(card);
    }

    // 카드 순서 변경
    @Transactional
    public void updateCardOrder(Long cardId, CardUpdateRequestDto requestDto) {
        Card card = cardAdapter.findById(cardId);
        if (requestDto.getCardOrder() != null) {
            updateCardOrder(card, requestDto.getCardOrder());
        }
    }

    // 카드 삭제
    @Transactional
    public void deleteCard(Long cardId, Long userId) {
        Card card = cardAdapter.findById(cardId);
        validateCardOwner(card, userId);
        cardAdapter.delete(card);
    }

    // 특정카드 순서 변경에따른 다른 카드 순서변경 메서드
    private void updateCardOrder(Card card, int newOrder) {
        List<Card> cards = cardAdapter.findAllByColumnId(card.getTaskColumn().getId());

        int oldOrder = card.getCardOrder();

        if (oldOrder < newOrder) {
            for (Card c : cards) {
                if (c.getCardOrder() > oldOrder && c.getCardOrder() <= newOrder) {
                    c.setCardOrder(c.getCardOrder() - 1);
                }
            }
        } else if (oldOrder > newOrder) {
            for (Card c : cards) {
                if (c.getCardOrder() < oldOrder && c.getCardOrder() >= newOrder) {
                    c.setCardOrder(c.getCardOrder() + 1);
                }
            }
        }

        card.setCardOrder(newOrder);
        cardAdapter.save(card);
        cardAdapter.saveAll(cards);
    }

    // 카드 생성자 권한 확인 메서드
    private void validateCardOwner(Card card, Long userId) {
        boolean isCreator = card.getCardUsers().stream()
                .anyMatch(cardUser -> cardUser.getUser().getId().equals(userId) && cardUser.isCreator());

        if (!isCreator) {
            throw new AccessDeniedException(ResponseCodeEnum.ACCESS_DENIED);
        }
    }

}
