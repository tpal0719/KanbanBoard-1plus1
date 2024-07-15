package com.sparta.springtrello.domain.checklist.entity;

import com.sparta.springtrello.common.Timestamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "checklist_items")
public class ChecklistItem extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Setter
    private String itemName;

    @Setter
    private boolean isCompleted;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id", nullable = false)
    private Checklist checklist;


    @Builder
    public ChecklistItem(String itemName, Checklist checklist) {
        this.itemName = itemName;
        this.checklist = checklist;
    }

    public void switchCompleted(){
        this.isCompleted = !this.isCompleted;
    }

}
