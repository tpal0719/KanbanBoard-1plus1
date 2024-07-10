package com.sparta.springtrello.domain.column.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "columns")
@NoArgsConstructor
public class Column extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @jakarta.persistence.Column(name = "column_name", nullable = false, length = 255)
    private String name;

    @jakarta.persistence.Column(name = "column_order", nullable = false)
    private int order;

    @Builder
    public Column(Board board, String name, int order) {
        this.board = board;
        this.name = name;
        this.order = order;
    }
}