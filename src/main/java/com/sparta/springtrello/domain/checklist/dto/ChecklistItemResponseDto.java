package com.sparta.springtrello.domain.checklist.dto;

import com.sparta.springtrello.domain.checklist.entity.ChecklistItem;
import lombok.Getter;

@Getter
public class ChecklistItemResponseDto {

    private String item_name;
    private boolean isCompleted;

    public ChecklistItemResponseDto(ChecklistItem checklistItem) {
        this.item_name = checklistItem.getItem_name();
        this.isCompleted = checklistItem.isCompleted();
    }
}
