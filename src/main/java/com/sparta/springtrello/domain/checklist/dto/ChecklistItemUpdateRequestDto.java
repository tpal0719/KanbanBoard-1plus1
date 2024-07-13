package com.sparta.springtrello.domain.checklist.dto;

import lombok.Getter;

@Getter
public class ChecklistItemUpdateRequestDto {

    private String item_name;
    private boolean isCompleted;

}
