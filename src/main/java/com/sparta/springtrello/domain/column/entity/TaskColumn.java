package com.sparta.springtrello.domain.column.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "task_columns")
@NoArgsConstructor
public class TaskColumn extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    private String columnName;

    private int columnOrder;

    @Builder
    public TaskColumn(Board board, String columnName, int columnOrder) {
        this.board = board;
        this.columnName = columnName;
        this.columnOrder = columnOrder;
    }
}