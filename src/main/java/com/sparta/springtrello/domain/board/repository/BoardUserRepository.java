package com.sparta.springtrello.domain.board.repository;

import com.sparta.springtrello.domain.board.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
    @Query("SELECT bu FROM BoardUser bu WHERE bu.board.id = :boardId AND bu.user.id = :userId")
    Optional<BoardUser> findByBoardIdAndUserId(Long boardId, Long userId);
}
