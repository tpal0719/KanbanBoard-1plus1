package com.sparta.springtrello.domain.comment.controller;

import com.sparta.springtrello.auth.UserDetailsImpl;
import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.comment.dto.CommentRequestDto;
import com.sparta.springtrello.domain.comment.dto.CommentResponseDto;
import com.sparta.springtrello.domain.comment.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //카드에 댓글 생성
    @PostMapping("/cards/{cardId}/comments")
    public ResponseEntity<HttpResponseDto<Void>> createComment(
            @PathVariable Long cardId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment( cardId, requestDto,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.CREATED);
    }

    //댓글 조회(카드별 전체)
    @GetMapping("/cards/{cardId}/comments")
    public ResponseEntity<HttpResponseDto<List<CommentResponseDto>>> getCommentsByCardId(@PathVariable Long cardId,
                                                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CommentResponseDto> comments = commentService.getCommentsByCardId(cardId,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK, comments);
    }

    //댓글 조회(단일)
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<HttpResponseDto<CommentResponseDto>> getOneComment(@PathVariable Long commentId,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        CommentResponseDto comment = commentService.getOneComment(commentId,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK, comment);
    }

    //댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<HttpResponseDto<Void>> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.updateComments(commentId, requestDto, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }

    //댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<HttpResponseDto<Void>> deleteCard(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }
}
