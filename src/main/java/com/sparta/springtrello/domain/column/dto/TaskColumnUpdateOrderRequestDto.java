package com.sparta.springtrello.domain.column.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskColumnUpdateOrderRequestDto {
    private List<Long> columnIds;
}