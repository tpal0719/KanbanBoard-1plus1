package com.sparta.springtrello.domain.card.entity;

import com.sparta.springtrello.common.Timestamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Card extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String cardName;

    @Column
    private String cardDescription;

    @NotNull
    @Column
    private LocalDateTime dueDate;

    @NotNull
    @Column(name = "order_priority")
    private Integer cardOrder;

    @ManyToOne
    @JoinColumn(name = "column_id", nullable = false)
    private TaskColumn column;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Card(String cardName, String cardDescription, Integer cardOrder, TaskColumn column) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
        this.cardOrder = cardOrder;
        this.column = column;
    }
}
