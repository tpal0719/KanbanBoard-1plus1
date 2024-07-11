package com.sparta.springtrello.domain.column.service;

import com.sparta.springtrello.domain.column.dto.TaskColumnCreateRequestDto;
import com.sparta.springtrello.domain.column.dto.TaskColumnResponseDto;
import com.sparta.springtrello.domain.column.dto.TaskColumnUpdateOrderRequestDto;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import com.sparta.springtrello.domain.column.repository.TaskColumnAdapter;
import com.sparta.springtrello.domain.board.repository.BoardAdapter;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.exception.custom.column.ColumnException;
import com.sparta.springtrello.common.ResponseCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskColumnService {

    private final TaskColumnAdapter taskColumnAdapter;
    private final BoardAdapter boardAdapter;

    @Transactional
    public void createTaskColumn(Long boardId, TaskColumnCreateRequestDto requestDto) {
        Board board = boardAdapter.findById(boardId);
        int columnOrder = board.getTaskColumns().size() + 1;

        TaskColumn taskColumn = TaskColumn.builder()
                .board(board)
                .columnName(requestDto.getColumnName())
                .columnOrder(columnOrder)
                .build();

        taskColumnAdapter.save(taskColumn);
    }

    @Transactional
    public void updateTaskColumnOrder(Long boardId, TaskColumnUpdateOrderRequestDto requestDto) {
        Board board = boardAdapter.findById(boardId);
        List<TaskColumn> columns = taskColumnAdapter.findAllByBoardOrderByColumnOrder(board);

        if (columns.size() != requestDto.getColumnIds().size()) {
            throw new ColumnException(ResponseCodeEnum.INVALID_COLUMN_ORDER);
        }

        Map<Long, TaskColumn> columnMap = columns.stream()
                .collect(Collectors.toMap(TaskColumn::getId, Function.identity()));

        List<TaskColumn> reorderedColumns = new ArrayList<>();

        for (int i = 0; i < requestDto.getColumnIds().size(); i++) {
            Long columnId = requestDto.getColumnIds().get(i);
            TaskColumn column = columnMap.get(columnId);
            if (column == null) {
                throw new ColumnException(ResponseCodeEnum.COLUMN_NOT_FOUND);
            }
            column.setColumnOrder(i + 1);
            reorderedColumns.add(column);
        }

        taskColumnAdapter.saveAll(reorderedColumns);
    }

    @Transactional(readOnly = true)
    public List<TaskColumnResponseDto> getTaskColumns(Long boardId) {
        Board board = boardAdapter.findById(boardId);
        List<TaskColumn> columns = taskColumnAdapter.findAllByBoardOrderByColumnOrder(board);
        return columns.stream()
                .map(TaskColumnResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTaskColumn(Long columnId) {
        TaskColumn taskColumn = taskColumnAdapter.findById(columnId);
        if (taskColumn == null) {
            throw new ColumnException(ResponseCodeEnum.COLUMN_NOT_FOUND);
        }
        taskColumnAdapter.delete(taskColumn);
    }

}