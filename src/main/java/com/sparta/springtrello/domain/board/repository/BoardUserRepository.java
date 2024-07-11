package com.sparta.springtrello.domain.board.repository;

import com.sparta.springtrello.domain.board.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
}
