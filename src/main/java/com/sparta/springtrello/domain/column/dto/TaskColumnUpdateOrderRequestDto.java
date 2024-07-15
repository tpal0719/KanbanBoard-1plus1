package com.sparta.springtrello.domain.column.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskColumnUpdateOrderRequestDto {
    private int columnOrder;
}