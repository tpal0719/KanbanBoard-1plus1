package com.sparta.springtrello.domain.checklist.repository;

import com.sparta.springtrello.domain.checklist.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    List<Checklist> findAllByCardId(Long cardId);

    @Query("SELECT " +
            "CASE WHEN (COUNT(ci) > 0) " +
            "THEN (SUM(CASE WHEN ci.isCompleted = true THEN 1 ELSE 0 END) * 1.0 / COUNT(ci)) " +
            "ELSE 0 END " +
            "FROM ChecklistItem ci " +
            "WHERE ci.checklist.id = :checklistId")
    Double calculateCompletionRate(@Param("checklistId") Long checklistId);
}
