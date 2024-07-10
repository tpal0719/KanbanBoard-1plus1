package com.sparta.springtrello.domain.column.service;

import com.sparta.springtrello.domain.column.dto.TaskColumnCreateRequestDto;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import com.sparta.springtrello.domain.column.repository.TaskColumnAdapter;
import com.sparta.springtrello.domain.board.repository.BoardAdapter;
import com.sparta.springtrello.domain.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
