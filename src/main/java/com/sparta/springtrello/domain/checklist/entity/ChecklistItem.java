package com.sparta.springtrello.domain.checklist.entity;

import com.sparta.springtrello.common.Timestamped;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "checklist_items")
public class ChecklistItem extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String item_name;
    private boolean isCompleted;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist", nullable = false)
    private Checklist checklist;


    @Builder
    public ChecklistItem(String item_name, boolean isCompleted, Checklist checklist) {
        this.item_name = item_name;
        this.isCompleted = isCompleted;
        this.checklist = checklist;
    }

}
