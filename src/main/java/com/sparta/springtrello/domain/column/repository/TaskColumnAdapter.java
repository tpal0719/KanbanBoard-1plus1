package com.sparta.springtrello.domain.column.repository;

import com.sparta.springtrello.domain.column.entity.TaskColumn;
import com.sparta.springtrello.exception.custom.column.ColumnException;
import com.sparta.springtrello.common.ResponseCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskColumnAdapter {
    private final TaskColumnRepository taskColumnRepository;

    public TaskColumn save(TaskColumn taskColumn) {
        return taskColumnRepository.save(taskColumn);
    }

    public TaskColumn findById(Long id) {
        return taskColumnRepository.findById(id)
                .orElseThrow(() -> new ColumnException(ResponseCodeEnum.COLUMN_NOT_FOUND));
    }
}
