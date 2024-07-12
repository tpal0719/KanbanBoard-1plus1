package com.sparta.springtrello.domain.card.service;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.common.S3Uploader;
import com.sparta.springtrello.domain.card.dto.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.dto.CardResponseDto;
import com.sparta.springtrello.domain.card.dto.CardUpdateRequestDto;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.entity.CardUser;
import com.sparta.springtrello.domain.card.entity.FileAttachment;
import com.sparta.springtrello.domain.card.repository.CardAdapter;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import com.sparta.springtrello.domain.column.repository.TaskColumnRepository;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.entity.UserRoleEnum;
import com.sparta.springtrello.domain.user.repository.UserAdapter;
import com.sparta.springtrello.exception.custom.card.CardException;
import com.sparta.springtrello.exception.custom.column.ColumnException;
import com.sparta.springtrello.exception.custom.common.AccessDeniedException;
import com.sparta.springtrello.exception.custom.common.UploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardAdapter cardAdapter;
    private final TaskColumnRepository taskColumnRepository;
    private final UserAdapter userAdapter;
    private final S3Uploader s3Uploader;

    // 카드 생성
    // 카드 생성
    @Transactional
    public void createCard(Long columnId, CardCreateRequestDto requestDto, Long userId) {
        TaskColumn taskColumn = taskColumnRepository.findById(columnId).orElseThrow(()->new ColumnException(ResponseCodeEnum.COLUMN_NOT_FOUND));
        if (taskColumn == null) {
            throw new CardException(ResponseCodeEnum.COLUMN_NOT_FOUND);
        }

        if (requestDto.getCardName() == null || requestDto.getCardName().isEmpty()) {
            throw new CardException(ResponseCodeEnum.CARD_NOT_NAME);
        }

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
    public void addCardMember(Long cardId, Long userId, Long requesterId) {
        Card card = cardAdapter.findById(cardId);
        validateCardOwnerOrManager(card, requesterId);  // 관리자 또는 카드 작성자 권한 확인

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
        validateCardOwnerOrManager(card, userId); // 관리자 또는 카드 작성자 권한 확인

        if (card.getTaskColumn() == null) {
            throw new CardException(ResponseCodeEnum.COLUMN_NOT_FOUND);
        }

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
        if (card.getTaskColumn() == null) {
            throw new CardException(ResponseCodeEnum.COLUMN_NOT_FOUND);
        }

        if (requestDto.getCardOrder() != null) {
            updateCardOrder(card, requestDto.getCardOrder());
        }
    }

    // 파일 업로드
    @Transactional
    public void uploadFileAttachment(Long cardId, MultipartFile file, String fileDescription, Long userId) {
        Card card = cardAdapter.findById(cardId);
        validateCardOwnerOrManager(card, userId);

        if (card.getTaskColumn() == null) {
            throw new CardException(ResponseCodeEnum.COLUMN_NOT_FOUND);
        }

        try {
            String fileUrl = s3Uploader.upload(file, "card-attachments");
            FileAttachment fileAttachment = FileAttachment.builder()
                    .fileUrl(fileUrl)
                    .fileDescription(fileDescription)
                    .card(card)
                    .build();
            cardAdapter.saveFileAttachment(fileAttachment);
        } catch (IOException e) {
            throw new UploadException(ResponseCodeEnum.UPLOAD_FAILED);
        }
    }

    // 파일 삭제
    @Transactional
    public void deleteFileAttachment(Long fileAttachmentId, Long userId) {
        FileAttachment fileAttachment = cardAdapter.findFileAttachmentById(fileAttachmentId);
        validateCardOwnerOrManager(fileAttachment.getCard(), userId);

        s3Uploader.delete(fileAttachment.getFileUrl());
        cardAdapter.deleteFileAttachment(fileAttachment);
    }

    // 파일 다운로드
    @Transactional(readOnly = true)
    public Resource downloadFileAttachment(Long fileAttachmentId) {
        FileAttachment fileAttachment = cardAdapter.findFileAttachmentById(fileAttachmentId);
        String fileUrl = fileAttachment.getFileUrl();

        if (!s3Uploader.doesObjectExist(fileUrl)) {
            throw new UploadException(ResponseCodeEnum.FILE_NOT_FOUND);
        }

        try {
            return s3Uploader.download(fileUrl);
        } catch (IOException e) {
            throw new UploadException(ResponseCodeEnum.UPLOAD_FAILED);
        }
    }

    // 카드 삭제
    @Transactional
    public void deleteCard(Long cardId, Long userId) {
        Card card = cardAdapter.findById(cardId);
        if (card == null) {
            throw new CardException(ResponseCodeEnum.CARD_NOT_FOUND); // 이미 삭제된 카드 예외 처리
        }
        validateCardOwnerOrManager(card, userId); // 관리자 또는 작성자 권한 확인
        cardAdapter.delete(card);
    }

    // 특정 카드 순서 변경에 따른 다른 카드 순서변경 메서드
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

    // 카드 생성자 또는 관리자 권한 확인 메서드
    private void validateCardOwnerOrManager(Card card, Long userId) {
        User user = userAdapter.findById(userId);
        boolean isManager = user.getUserRole() == UserRoleEnum.ROLE_MANAGER; // 매니저인지 확인

        boolean isCreator = card.getCardUsers().stream()
                .anyMatch(cardUser -> cardUser.getUser().getId().equals(userId) && cardUser.isCreator());

        if (!isCreator && !isManager) {
            throw new AccessDeniedException(ResponseCodeEnum.ACCESS_DENIED);
        }
    }
}
