package com.sparta.springtrello.domain.column.repository;

import com.sparta.springtrello.domain.column.entity.TaskColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskColumnRepository extends JpaRepository<TaskColumn, Long> {
}
