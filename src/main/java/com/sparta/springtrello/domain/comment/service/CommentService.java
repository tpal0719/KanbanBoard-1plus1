package com.sparta.springtrello.domain.comment.service;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardAdapter;
import com.sparta.springtrello.domain.comment.dto.CommentRequestDto;
import com.sparta.springtrello.domain.comment.dto.CommentResponseDto;
import com.sparta.springtrello.domain.comment.entity.Comment;
import com.sparta.springtrello.domain.comment.repository.CommentRepository;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.entity.UserRoleEnum;
import com.sparta.springtrello.exception.custom.Comment.CommentException;
import com.sparta.springtrello.exception.custom.common.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CardAdapter cardAdapter;


    //댓글 생성
    public void createComment(Long cardId, CommentRequestDto requestDto,User user) {
        Card card = cardAdapter.findById(cardId);

        Comment comment = Comment.builder()
                .user(user)
                .card(card)
                .content(requestDto.getContent())
                .build();
        commentRepository.save(comment);
    }

    //카드별 댓글 조회
    public List<CommentResponseDto> getCommentsByCardId(Long cardId,User user) {
        List<Comment> comments = commentRepository.findAllByCardId(cardId);
        return CommentResponseDto.fromEntities(comments);
    }

    // 댓글 조회(단일)
    public CommentResponseDto getOneComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentException(ResponseCodeEnum.COMMENT_NOT_FOUND));;
        return new CommentResponseDto(comment);
    }

    //댓글 수정
    @Transactional
    public void updateComments(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentException(ResponseCodeEnum.COMMENT_NOT_FOUND));;
        validateCommentWriterOrManager(comment.getUser().getId(), user);

        if (requestDto.getContent() != null) {
            comment.setContent(requestDto.getContent());
        }
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentException(ResponseCodeEnum.COMMENT_NOT_FOUND));
        validateCommentWriterOrManager(comment.getUser().getId(), user);

        commentRepository.delete(comment);
    }


    /* Utils */

    //작성자 or 매니저 인가?
    private void validateCommentWriterOrManager(Long commentUserId, User user) {

        if (!commentUserId.equals(user.getId()) && !user.getUserRole().equals(UserRoleEnum.ROLE_MANAGER)) {
            throw new AccessDeniedException(ResponseCodeEnum.ACCESS_DENIED);
        }
    }



}
