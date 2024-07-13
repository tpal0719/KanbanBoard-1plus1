package com.sparta.springtrello.domain.checklist.dto;

import com.sparta.springtrello.domain.checklist.entity.Checklist;
import com.sparta.springtrello.domain.checklist.entity.ChecklistItem;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ChecklistResponseDto {

    private String checklistName;
    private float percentComplete;
    private List<ChecklistItem> checklistItems;

    public ChecklistResponseDto(Checklist checklist) {
        this.checklistName = checklist.getChecklistName();
        this.percentComplete = checklist.getPercentComplete();
        this.checklistItems = checklist.getChecklistItems();
    }

    public static List<ChecklistResponseDto> fromEntities(List<Checklist> checklists) {
        return checklists.stream().map(ChecklistResponseDto::new).collect(Collectors.toList());
    }
}
