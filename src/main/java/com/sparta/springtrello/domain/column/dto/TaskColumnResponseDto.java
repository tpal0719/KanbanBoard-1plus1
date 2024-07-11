package com.sparta.springtrello.domain.column.dto;

import com.sparta.springtrello.domain.column.entity.TaskColumn;
import lombok.Getter;

@Getter
public class TaskColumnResponseDto {
    private Long id;
    private String columnName;
    private int columnOrder;

    public TaskColumnResponseDto(TaskColumn taskColumn) {
        this.id = taskColumn.getId();
        this.columnName = taskColumn.getColumnName();
        this.columnOrder = taskColumn.getColumnOrder();
    }
}