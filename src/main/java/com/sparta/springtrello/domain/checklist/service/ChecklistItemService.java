package com.sparta.springtrello.domain.checklist.service;

import com.sparta.springtrello.domain.checklist.dto.ChecklistCreateRequestDto;
import com.sparta.springtrello.domain.checklist.dto.ChecklistResponseDto;
import com.sparta.springtrello.domain.checklist.dto.ChecklistUpdateRequestDto;
import com.sparta.springtrello.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecklistItemService {
    public void createChecklistItem(Long checklistId,ChecklistCreateRequestDto requestDto, User user) {

    }

    public ChecklistResponseDto getChecklistItem(Long checklistId, User user) {
        return null;
    }

    public void updateChecklistItem(Long checklistItemId, ChecklistUpdateRequestDto requestDto, User user) {

    }

    public void deleteChecklistItem(Long checklistItemId, User user) {

    }
}
