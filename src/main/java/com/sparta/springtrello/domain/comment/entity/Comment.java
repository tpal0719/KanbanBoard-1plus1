package com.sparta.springtrello.domain.comment.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.card.entity.Card;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name="comments")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column
    private String content;

    @Builder
    public Comment(Card card, User user, String content) {
        this.card = card;
        this.user = user;
        this.content = content;
    }
}
