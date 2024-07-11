package com.sparta.springtrello.domain.card.repository;

import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.entity.CardUser;
import com.sparta.springtrello.domain.card.entity.FileAttachment;
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
    private final FileAttachmentRepository fileAttachmentRepository;

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

    public FileAttachment saveFileAttachment(FileAttachment fileAttachment) {
        return fileAttachmentRepository.save(fileAttachment);
    }

    public FileAttachment findFileAttachmentById(Long fileAttachmentId) {
        return fileAttachmentRepository.findById(fileAttachmentId).orElseThrow(() -> new CardException(ResponseCodeEnum.FILE_NOT_FOUND));
    }

    public void deleteFileAttachment(FileAttachment fileAttachment) {
        fileAttachmentRepository.delete(fileAttachment);
    }

    public void delete(Card card) {
        cardRepository.delete(card);
    }

    public void saveAll(List<Card> cards) {
        cardRepository.saveAll(cards);
    }
}
