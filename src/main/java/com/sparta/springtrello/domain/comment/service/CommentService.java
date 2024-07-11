package com.sparta.springtrello.domain.comment.service;

import com.sparta.springtrello.auth.UserDetailsImpl;
import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardAdapter;
import com.sparta.springtrello.domain.comment.dto.CommentRequestDto;
import com.sparta.springtrello.domain.comment.dto.CommentResponseDto;
import com.sparta.springtrello.domain.comment.entity.Comment;
import com.sparta.springtrello.domain.comment.repository.CommentAdapter;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.repository.UserAdapter;
import com.sparta.springtrello.exception.custom.common.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentAdapter commentAdapter;
    private final CardAdapter cardAdapter;
    private final UserAdapter userAdapter;


    //댓글 생성
    public void createComment(UserDetailsImpl userDetails, Long cardId, CommentRequestDto requestDto) {
        Card card = cardAdapter.findById(cardId);
        User user = userAdapter.findById(userDetails.getUser().getId());

        Comment comment = Comment.builder()
                .user(user)
                .card(card)
                .content(requestDto.getContent())
                .build();
        commentAdapter.save(comment);
    }

    //카드별 댓글 조회
    public List<CommentResponseDto> getCommentsByCardId(Long cardId) {
        List<Comment> comments = commentAdapter.findAllByCardId(cardId);
        return CommentResponseDto.fromEntities(comments);
    }

    //댓글 수정
    @Transactional
    public void updateComments(Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentAdapter.findById(commentId);
        validateCommentWriter(comment, userDetails);

        if (requestDto.getContent() != null) {
            comment.setContent(requestDto.getContent());
        }
        commentAdapter.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentAdapter.findById(commentId);
        validateCommentWriter(comment, userDetails);
        commentAdapter.delete(comment);
    }

    private void validateCommentWriter(Comment comment, UserDetailsImpl userDetails) {
        if (!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new AccessDeniedException(ResponseCodeEnum.ACCESS_DENIED);
        }
    }
}
