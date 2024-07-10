package com.sparta.springtrello.domain.board.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "boards")
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String boardName;

    @Column
    private String boardDescription;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<TaskColumn> taskColumns = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<BoardUser> boardUsers = new ArrayList<>();

    @Builder
    public Board(String boardName, String boardDescription) {
        this.boardName = boardName;
        this.boardDescription = boardDescription;
    }
}
