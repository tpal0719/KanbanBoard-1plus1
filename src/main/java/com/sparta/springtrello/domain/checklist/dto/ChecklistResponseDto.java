package com.sparta.springtrello.domain.checklist.dto;

import com.sparta.springtrello.domain.checklist.entity.Checklist;
import com.sparta.springtrello.domain.checklist.entity.ChecklistItem;

import java.util.List;

public class ChecklistResponseDto {

    private String checklistName;
    private List<ChecklistItem> checklistItems;

    public ChecklistResponseDto(Checklist checklist) {
        this.checklistName = checklist.getChecklistName();
        this.checklistItems = checklist.getChecklistItems();
    }
}
