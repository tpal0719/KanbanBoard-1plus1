package com.sparta.springtrello.domain.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sparta.springtrello.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board_users")
public class BoardUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Setter
    @Getter
    @NotNull
    @Column
    private boolean accepted;

    @Builder
    public BoardUser(User user, Board board, boolean accepted) {
        this.user = user;
        this.board = board;
        this.accepted = accepted;
    }
}
