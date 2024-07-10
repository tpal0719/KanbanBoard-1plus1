package com.sparta.springtrello.domain.board.entity;


import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import com.sparta.springtrello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "boards")
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String boardName;

    private String boardDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    //컬럼
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_column", nullable = false)
    private TaskColumn taskColumn;


    @Builder
    public Board(String boardName, String boardDescription, User user, TaskColumn taskColumn) {
        this.boardName = boardName;
        this.boardDescription = boardDescription;
        this.user = user;
        this.taskColumn = taskColumn;
    }


}
