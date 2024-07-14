package com.sparta.springtrello.domain.checklist.service;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.domain.card.entity.CardUser;
import com.sparta.springtrello.domain.checklist.dto.*;
import com.sparta.springtrello.domain.checklist.entity.Checklist;
import com.sparta.springtrello.domain.checklist.entity.ChecklistItem;
import com.sparta.springtrello.domain.checklist.repository.ChecklistItemRepository;
import com.sparta.springtrello.domain.checklist.repository.ChecklistRepository;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.entity.UserRoleEnum;
import com.sparta.springtrello.exception.custom.checklist.ChecklistException;
import com.sparta.springtrello.exception.custom.common.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChecklistItemService {

    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;

    @Transactional
    public void createChecklistItem(Long checklistId, ChecklistItemCreateRequestDto requestDto, User user) {

        Checklist checklist = getChecklist(checklistId);
        validateCardMemberOrManager(checklist.getId(),user);
        ChecklistItem checklistItem = ChecklistItem.builder()
                .itemName(requestDto.getItemName())
                .build();

        checklistItemRepository.save(checklistItem);
    }

    @Transactional(readOnly = true)
    public ChecklistItemResponseDto getChecklistItem(Long checklistItemId, User user) {

        ChecklistItem checklistItem = getChecklistItem(checklistItemId);
        return new ChecklistItemResponseDto(checklistItem);
    }

    @Transactional
    public void updateChecklistItem(Long checklistItemId, ChecklistItemUpdateRequestDto requestDto, User user) {
        ChecklistItem checklistItem = getChecklistItem(checklistItemId);

        validateCardMemberOrManager(checklistItem.getChecklist().getId(),user);
        if(requestDto.getItemName().isBlank() || requestDto.getItemName().isEmpty()){
            checklistItem.setItemName(requestDto.getItemName());
        }

    }

    @Transactional
    public void toggleChecklistItem(Long checklistItemId, User user) {
        ChecklistItem checklistItem = getChecklistItem(checklistItemId);
        checklistItem.switchCompleted();
    }

    @Transactional
    public void deleteChecklistItem(Long checklistItemId, User user) {
        ChecklistItem checklistItem = getChecklistItem(checklistItemId);
        validateCardMemberOrManager(checklistItem.getChecklist().getId(),user);
        checklistItemRepository.delete(checklistItem);
    }




    /* Utils */

    public Checklist getChecklist(Long checklistId) {
        return checklistRepository.findById(checklistId).orElseThrow(()-> new ChecklistException(ResponseCodeEnum.CHECKLIST_NOT_FOUND));
    }

    public ChecklistItem getChecklistItem(Long checklistItemId) {
        return checklistItemRepository.findById(checklistItemId).orElseThrow(()->new ChecklistException(ResponseCodeEnum.CHECKLISTITEM_NOT_FOUND));
    }

    public void validateCardMemberOrManager(Long checklistId,User user){

        Checklist checklist = getChecklist(checklistId);

        // 카드에 권한이 있는 유저?
        for (CardUser cardUser : checklist.getCard().getCardUsers()) {
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
