package com.sparta.springtrello.domain.checklist.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.card.entity.Card;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "checklists")
public class Checklist extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Setter
    private String checklistName;

//    @Setter
//    private float percentComplete;


    @OneToMany(mappedBy = "checklistItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<ChecklistItem> checklistItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card", nullable = false)
    private Card card;

    @Builder
    public Checklist(String checklistName/*, float percent_complete*/, Card card) {
        this.checklistName = checklistName;
        //this.percentComplete = percent_complete;
        this.card = card;
    }

}
