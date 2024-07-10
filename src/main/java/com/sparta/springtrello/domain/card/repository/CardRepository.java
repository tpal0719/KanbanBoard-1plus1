package com.sparta.springtrello.domain.card.repository;

import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    int countByTaskColumn(TaskColumn taskColumn);
}
