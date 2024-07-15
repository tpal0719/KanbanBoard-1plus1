package com.sparta.springtrello.domain.checklist.service;


import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.entity.CardUser;
import com.sparta.springtrello.domain.card.repository.CardRepository;
import com.sparta.springtrello.domain.checklist.dto.ChecklistCreateRequestDto;
import com.sparta.springtrello.domain.checklist.dto.ChecklistResponseDto;
import com.sparta.springtrello.domain.checklist.dto.ChecklistUpdateRequestDto;
import com.sparta.springtrello.domain.checklist.entity.Checklist;
import com.sparta.springtrello.domain.checklist.entity.ChecklistItem;
import com.sparta.springtrello.domain.checklist.repository.ChecklistRepository;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.entity.UserRoleEnum;
import com.sparta.springtrello.exception.custom.card.CardException;
import com.sparta.springtrello.exception.custom.checklist.ChecklistException;
import com.sparta.springtrello.exception.custom.common.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final CardRepository cardRepository;
    private final ChecklistRepository checklistRepository;

    @Transactional
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

    public double calculateCompletionRate(Long checklistId) {
        Checklist checklist = getChecklist(checklistId);

        List<ChecklistItem> items = checklist.getChecklistItems();
        if (items.isEmpty()) {
            return 0.0;
        }

        long completedItems = items.stream()
                .filter(ChecklistItem::isCompleted)
                .count();

        return (double) completedItems / items.size();
    }

    @Transactional
    public void updateChecklist(Long checklistId, ChecklistUpdateRequestDto requestDto, User user) {

        Checklist checklist = getChecklist(checklistId);
        validateCardWriterOrManager(checklist.getCard().getId(), user);

        if (requestDto.getChecklistName() != null) {
            checklist.setChecklistName(requestDto.getChecklistName());
        }

    }

    @Transactional
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
        if (user.getUserRole().equals(UserRoleEnum.ROLE_MANAGER)) {
            return;
        }

        // 작성자도 매니저도 아님
        throw new AccessDeniedException(ResponseCodeEnum.ACCESS_DENIED);
    }


}
