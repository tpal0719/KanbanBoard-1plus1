package com.sparta.springtrello.domain.checklist.service;


import com.sparta.springtrello.domain.checklist.dto.ChecklistCreateRequestDto;
import com.sparta.springtrello.domain.checklist.dto.ChecklistResponseDto;
import com.sparta.springtrello.domain.checklist.dto.ChecklistUpdateRequestDto;
import com.sparta.springtrello.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    public void createChecklist(Long cardId,ChecklistCreateRequestDto requestDto, User user) {
    }

    public List<ChecklistResponseDto> getChecklistsByCard(Long cardId, User user) {

        return null;
    }

    public ChecklistResponseDto getOneChecklist(Long checklistId, User user) {

        return null;
    }

    public void updateChecklist(Long checklistId, ChecklistUpdateRequestDto requestDto, User user) {

    }

    public void deleteChecklist(Long checklistId, User user) {

    }

}
