package com.sparta.springtrello.domain.comment.repository;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.domain.comment.entity.Comment;
import com.sparta.springtrello.exception.custom.Comment.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentAdapter {
    private final CommentRepository commentRepository;

    public List<Comment> findAllByCardId(Long cardId) {
        return commentRepository.findAllByCardId(cardId);
    }
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentException(ResponseCodeEnum.COMMENT_NOT_FOUND));
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
