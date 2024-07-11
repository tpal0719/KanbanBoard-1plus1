package com.sparta.springtrello.domain.board.dto;

import com.sparta.springtrello.domain.board.entity.Board;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDto {

    private String boardName;
    private String boardDescription;

    public BoardResponseDto(Board board) {
        this.boardName = board.getBoardName();
        this.boardDescription = board.getBoardDescription();
    }

    public static List<BoardResponseDto> fromEntities(List<Board> boards) {
        return boards.stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }
}
