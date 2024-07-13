package com.sparta.springtrello.domain.checklist.repository;

import com.sparta.springtrello.domain.checklist.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
}
