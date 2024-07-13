package com.sparta.springtrello.domain.card.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import com.sparta.springtrello.domain.comment.entity.Comment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Card extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotNull
    @Column
    private String cardName;

    @Setter
    @Column
    private String cardDescription;

    @Setter
    @Column
    private LocalDateTime dueDate;

    @Setter
    @NotNull
    @Column
    private Integer cardOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_column", nullable = false)
    private TaskColumn taskColumn;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "card_user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<CardUser> cardUsers = new ArrayList<>();

    @Builder
    public Card(String cardName, Integer cardOrder, TaskColumn taskColumn) {
        this.cardName = cardName;
        this.cardOrder = cardOrder;
        this.taskColumn = taskColumn;
    }
}
