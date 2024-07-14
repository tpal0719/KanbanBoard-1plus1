package com.sparta.springtrello.domain.checklist.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
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

    @NotNull
    @Setter
    private boolean isCompleted;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist", nullable = false)
    private Checklist checklist;


    @Builder
    public ChecklistItem(String itemName, boolean isCompleted, Checklist checklist) {
        this.itemName = itemName;
        this.isCompleted = isCompleted;
        this.checklist = checklist;
    }

    public void switchCompleted(){
        this.isCompleted = !this.isCompleted;
    }

}
