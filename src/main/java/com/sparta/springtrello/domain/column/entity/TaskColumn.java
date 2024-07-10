package com.sparta.springtrello.domain.column.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.card.entity.Card;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @NotNull
    @Column
    private String columnName;

    @NotNull
    @Column
    private int columnOrder;

    @OneToMany(mappedBy = "taskColumn", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<Card> cards = new ArrayList<>();

    @Builder
    public TaskColumn(Board board, String columnName, int columnOrder) {
        this.board = board;
        this.columnName = columnName;
        this.columnOrder = columnOrder;
    }
}
