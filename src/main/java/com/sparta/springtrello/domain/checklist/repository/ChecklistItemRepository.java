package com.sparta.springtrello.domain.checklist.repository;

import com.sparta.springtrello.domain.checklist.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
}
