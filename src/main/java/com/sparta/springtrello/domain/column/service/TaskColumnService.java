package com.sparta.springtrello.domain.column.service;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.board.repository.BoardAdapter;
import com.sparta.springtrello.domain.board.repository.BoardUserAdapter;
import com.sparta.springtrello.domain.column.dto.TaskColumnCreateRequestDto;
import com.sparta.springtrello.domain.column.dto.TaskColumnResponseDto;
import com.sparta.springtrello.domain.column.dto.TaskColumnUpdateRequestDto;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import com.sparta.springtrello.domain.column.repository.TaskColumnAdapter;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.entity.UserRoleEnum;
import com.sparta.springtrello.exception.custom.column.ColumnException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskColumnService {

    private final TaskColumnAdapter taskColumnAdapter;
    private final BoardAdapter boardAdapter;

    // 컬럼 생성
    @Transactional
    public void createTaskColumn(Long boardId, TaskColumnCreateRequestDto requestDto, User user) {
        validateColumnManager(user);

        Board board = boardAdapter.findById(boardId);
        int columnOrder = board.getTaskColumns().size() + 1;

        TaskColumn taskColumn = TaskColumn.builder()
                .board(board)
                .columnName(requestDto.getColumnName())
                .columnOrder(columnOrder)
                .build();

        taskColumnAdapter.save(taskColumn);
    }

    // 컬럼 순서 변경
    @Transactional
    public void updateTaskColumnOrder(Long columnId, int newOrder, User user) {
        validateColumnManager(user);

        TaskColumn column = taskColumnAdapter.findById(columnId);
        Board board = column.getBoard();
        List<TaskColumn> columns = taskColumnAdapter.findAllByBoardOrderByColumnOrder(board);

        int oldOrder = column.getColumnOrder();

        if (oldOrder < newOrder) {
            for (TaskColumn c : columns) {
                if (c.getColumnOrder() > oldOrder && c.getColumnOrder() <= newOrder) {
                    c.setColumnOrder(c.getColumnOrder() - 1);
                }
            }
        } else if (oldOrder > newOrder) {
            for (TaskColumn c : columns) {
                if (c.getColumnOrder() < oldOrder && c.getColumnOrder() >= newOrder) {
                    c.setColumnOrder(c.getColumnOrder() + 1);
                }
            }
        }

        column.setColumnOrder(newOrder);
        taskColumnAdapter.save(column);
        taskColumnAdapter.saveAll(columns);
    }

    // 컬럼 조회 (전체)
    @Transactional(readOnly = true)
    public List<TaskColumnResponseDto> getTaskColumns(Long boardId, User user) {
        isUserInBoard(user, boardId);

        Board board = boardAdapter.findById(boardId);
        List<TaskColumn> columns = taskColumnAdapter.findAllByBoardOrderByColumnOrder(board);
        return columns.stream()
                .map(TaskColumnResponseDto::new)
                .collect(Collectors.toList());
    }

    // 컬럼 조회 (단건)
    @Transactional(readOnly = true)
    public TaskColumnResponseDto getOneTaskColumn(Long columnId, User user) {
        TaskColumn column = taskColumnAdapter.findById(columnId);
        isUserInBoard(user, column.getBoard().getId());

        return new TaskColumnResponseDto(column);
    }

    //컬럼 수정
    @Transactional
    public void updateTaskColumn(Long columnId, TaskColumnUpdateRequestDto taskColumnUpdateRequestDto, User user) {
        validateColumnManager(user);
        TaskColumn column = taskColumnAdapter.findById(columnId);
        column.setColumnName(taskColumnUpdateRequestDto.getColumnName());
    }

    // 컬럼 삭제
    @Transactional
    public void deleteTaskColumn(Long columnId, User user) {
        validateColumnManager(user);

        TaskColumn taskColumn = taskColumnAdapter.findById(columnId);
        if (taskColumn == null) {
            throw new ColumnException(ResponseCodeEnum.COLUMN_NOT_FOUND);
        }
        taskColumnAdapter.delete(taskColumn);
    }

    /* Utils */

    // 관리자인가?
    private void validateColumnManager(User user) {
        if (!user.getUserRole().equals(UserRoleEnum.ROLE_MANAGER)) {
            throw new ColumnException(ResponseCodeEnum.ACCESS_DENIED);
        }
    }

    // 보드에 소속된 유저인가? - 컬럼 조작은 초대된 유저 or 매니저만 할수 있음
    private void isUserInBoard(User user, Long boardId) {
        //매니저인가?
        if (user.getUserRole().equals(UserRoleEnum.ROLE_MANAGER)) {
            return;
        }

        // 초대된 유저인가?
        Board board = boardAdapter.findById(boardId);
        for (var users : board.getBoardUsers()) {
            if (users.getUser().equals(user)) {
                return;
            }
        }

        //매니저도 초대된 유저도 아님
        throw new ColumnException(ResponseCodeEnum.ACCESS_DENIED);
    }
}