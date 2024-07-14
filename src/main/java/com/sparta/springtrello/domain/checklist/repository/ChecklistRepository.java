package com.sparta.springtrello.domain.checklist.repository;

import com.sparta.springtrello.domain.checklist.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    List<Checklist> findAllByCardId(Long cardId);

    @Query("")
    float percentCaculator(Long checklistId);
}
