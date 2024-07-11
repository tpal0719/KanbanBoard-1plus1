package com.sparta.springtrello.domain.comment.dto;

import com.sparta.springtrello.domain.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentResponseDto {
    private String username;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    public static List<CommentResponseDto> fromEntities(List<Comment> comments) {
        return comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
