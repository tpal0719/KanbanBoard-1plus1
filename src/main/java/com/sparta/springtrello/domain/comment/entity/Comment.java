package com.sparta.springtrello.domain.comment.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.smartcardio.Card;

@Getter
@Entity
@NoArgsConstructor
@Table(name="comment")
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

    @Column(nullable = false)
    private String content;

    @Builder
    public Comment(Card card, User user, String content) {
        this.card = card;
        this.user = user;
        this.content = content;
    }
}
