package com.sparta.springtrello.domain.checklist.dto;

import com.sparta.springtrello.domain.checklist.entity.ChecklistItem;
import lombok.Getter;

import java.util.List;

@Getter
public class ChecklistItemResponseDto {

    private String itemName;
    private boolean isCompleted;

    public ChecklistItemResponseDto(ChecklistItem checklistItem) {
        this.itemName = checklistItem.getItemName();
        this.isCompleted = checklistItem.isCompleted();
    }
}
