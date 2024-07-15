package com.sparta.springtrello.domain.checklist.dto;

import com.sparta.springtrello.domain.checklist.entity.Checklist;
import com.sparta.springtrello.domain.checklist.entity.ChecklistItem;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ChecklistResponseDto {

    private String checklistName;
    private List<ChecklistItemResponseDto> checklistItems;

    public ChecklistResponseDto(Checklist checklist) {
        this.checklistName = checklist.getChecklistName();
        this.checklistItems = checklist.getChecklistItems().stream().map(ChecklistItemResponseDto::new).collect(Collectors.toList());
    }

    public static List<ChecklistResponseDto> fromEntities(List<Checklist> checklists) {
        return checklists.stream().map(ChecklistResponseDto::new).collect(Collectors.toList());
    }
}
