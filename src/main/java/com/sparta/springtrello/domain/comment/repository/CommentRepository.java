package com.sparta.springtrello.domain.comment.repository;

import com.sparta.springtrello.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCardId(Long cardId);
}