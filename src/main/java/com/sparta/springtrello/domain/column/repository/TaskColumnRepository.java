package com.sparta.springtrello.domain.column.repository;

import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskColumnRepository extends JpaRepository<TaskColumn, Long> {
    List<TaskColumn> findAllByBoardOrderByColumnOrder(Board board);

}