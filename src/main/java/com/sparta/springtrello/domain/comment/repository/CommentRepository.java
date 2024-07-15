package com.sparta.springtrello.domain.comment.repository;

import com.sparta.springtrello.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.card.id = :cardId")
    List<Comment> findAllByCardId(Long cardId);
}
