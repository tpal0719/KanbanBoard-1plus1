package com.sparta.springtrello.domain.column.repository;

import com.sparta.springtrello.domain.column.entity.TaskColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskColumnRepository extends JpaRepository<TaskColumn, Long> {

    @Query("SELECT tc FROM TaskColumn tc JOIN FETCH tc.board WHERE tc.board.id = :boardId ORDER BY tc.columnOrder")
    List<TaskColumn> findAllByBoardIdOrderByColumnOrder(@Param("boardId") Long boardId);

    @Modifying
    @Query("UPDATE TaskColumn tc SET tc.columnOrder = tc.columnOrder - 1 WHERE tc.board.id = :boardId AND tc.columnOrder > :oldOrder AND tc.columnOrder <= :newOrder")
    void decreaseOrderForIntermediateColumns(@Param("boardId") Long boardId, @Param("oldOrder") int oldOrder, @Param("newOrder") int newOrder);

    @Modifying
    @Query("UPDATE TaskColumn tc SET tc.columnOrder = tc.columnOrder + 1 WHERE tc.board.id = :boardId AND tc.columnOrder >= :newOrder AND tc.columnOrder < :oldOrder")
    void increaseOrderForIntermediateColumns(@Param("boardId") Long boardId, @Param("oldOrder") int oldOrder, @Param("newOrder") int newOrder);

    @Modifying
    @Query("UPDATE TaskColumn tc SET tc.columnOrder = :newOrder WHERE tc.id = :columnId")
    void updateColumnOrder(@Param("columnId") Long columnId, @Param("newOrder") int newOrder);

    @Query("SELECT COALESCE(MAX(tc.columnOrder), 0) FROM TaskColumn tc WHERE tc.board.id = :boardId")
    int findMaxColumnOrderByBoardId(@Param("boardId") Long boardId);
}