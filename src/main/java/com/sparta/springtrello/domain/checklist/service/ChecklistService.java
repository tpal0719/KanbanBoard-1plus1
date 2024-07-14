package com.sparta.springtrello.domain.checklist.service;


import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.entity.CardUser;
import com.sparta.springtrello.domain.card.repository.CardRepository;
import com.sparta.springtrello.domain.checklist.dto.ChecklistCreateRequestDto;
import com.sparta.springtrello.domain.checklist.dto.ChecklistResponseDto;
import com.sparta.springtrello.domain.checklist.dto.ChecklistUpdateRequestDto;
import com.sparta.springtrello.domain.checklist.entity.Checklist;
import com.sparta.springtrello.domain.checklist.repository.ChecklistRepository;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.entity.UserRoleEnum;
import com.sparta.springtrello.exception.custom.board.BoardException;
import com.sparta.springtrello.exception.custom.card.CardException;
import com.sparta.springtrello.exception.custom.checklist.ChecklistException;
import com.sparta.springtrello.exception.custom.column.ColumnException;
import com.sparta.springtrello.exception.custom.common.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final CardRepository cardRepository;
    private final ChecklistRepository checklistRepository;

    public void createChecklist(Long cardId, ChecklistCreateRequestDto requestDto, User user) {

        Card card = getCard(cardId);
        validateCardWriterOrManager(card.getId(), user);

        Checklist checklist = Checklist.builder()
                .checklistName(requestDto.getChecklistName())
                .card(card)
                .build();

        checklistRepository.save(checklist);
    }

    public List<ChecklistResponseDto> getChecklistsByCard(Long cardId, User user) {

        List<Checklist> checklists = checklistRepository.findAllByCardId(cardId);
        return ChecklistResponseDto.fromEntities(checklists);
    }

    public ChecklistResponseDto getOneChecklist(Long checklistId, User user) {
        Checklist checklist = getChecklist(checklistId);
        return new ChecklistResponseDto(checklist);
    }

    public void updateChecklist(Long checklistId, ChecklistUpdateRequestDto requestDto, User user) {

        Checklist checklist = getChecklist(checklistId);
        validateCardWriterOrManager(checklist.getCard().getId(), user);

        if (requestDto.getChecklistName() != null) {
            checklist.setChecklistName(requestDto.getChecklistName());
        }

    }

    public void deleteChecklist(Long checklistId, User user) {

        Checklist checklist = getChecklist(checklistId);
        validateCardWriterOrManager(checklist.getCard().getId(), user);

        checklistRepository.delete(checklist);
    }



    /* Utils */


    public Card getCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new CardException(ResponseCodeEnum.CARD_NOT_FOUND));
    }

    public Checklist getChecklist(Long checklistId) {
        return checklistRepository.findById(checklistId).orElseThrow(() -> new ChecklistException(ResponseCodeEnum.CHECKLIST_NOT_FOUND));
    }

    //작성자 or 매니저 인가?
    private void validateCardWriterOrManager(Long cardId, User user) {
        Card card = getCard(cardId);

        // 카드에 권한이 있는 유저?
        for (CardUser cardUser : card.getCardUsers()) {
            if(cardUser.getUser().equals(user)) {
                return;
            }
        }

        // 매니저?
        if (!user.getUserRole().equals(UserRoleEnum.ROLE_MANAGER)) {
            return;
        }

        // 작성자도 매니저도 아님
        throw new AccessDeniedException(ResponseCodeEnum.ACCESS_DENIED);
    }

}
