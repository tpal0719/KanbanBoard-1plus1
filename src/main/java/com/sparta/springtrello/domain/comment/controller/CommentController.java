package com.sparta.springtrello.domain.comment.controller;

import com.sparta.springtrello.auth.UserDetailsImpl;
import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.card.dto.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.dto.CardResponseDto;
import com.sparta.springtrello.domain.card.dto.CardUpdateRequestDto;
import com.sparta.springtrello.domain.comment.dto.CommentRequestDto;
import com.sparta.springtrello.domain.comment.dto.CommentResponseDto;
import com.sparta.springtrello.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    //카드에 댓글 생성
    @PostMapping("/cards/{cardId}")
    public ResponseEntity<HttpResponseDto<Void>> createComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long cardId,
            @RequestBody CommentRequestDto requestDto) {
        commentService.createComment(userDetails, cardId, requestDto);
        return ResponseUtils.success(HttpStatus.CREATED);
    }

    //카드별 댓글 조회
    @GetMapping("/cards/{cardId}")
    public ResponseEntity<HttpResponseDto<List<CommentResponseDto>>> getCommentsByCardId(@PathVariable Long cardId) {
        List<CommentResponseDto> Comments = commentService.getCommentsByCardId(cardId);
        return ResponseUtils.success(HttpStatus.OK, Comments);
    }

    //댓글 수정
    @PostMapping("/{commentId}")
    public ResponseEntity<HttpResponseDto<Void>> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.updateComments(commentId, requestDto, userDetails);
        return ResponseUtils.success(HttpStatus.OK);
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpResponseDto<Void>> deleteCard(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails);
        return ResponseUtils.success(HttpStatus.OK);
    }
}
