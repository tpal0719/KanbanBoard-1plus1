package com.sparta.springtrello.domain.board.dto;

import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.board.entity.BoardUser;
import lombok.Getter;


import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDto {
    private Long boardId;
    private String boardName;
    private String boardDescription;

    private List<String> boardMembers;

    public BoardResponseDto(Board board) {
        this.boardId = board.getId(); // boardId 추가
        this.boardName = board.getBoardName();
        this.boardDescription = board.getBoardDescription();

        this.boardMembers = board.getBoardUsers().stream()
                .filter(BoardUser::isAccepted)
                .map(boardUser -> boardUser.getUser().getUsername())
                .collect(Collectors.toList());
    }



    public static List<BoardResponseDto> fromEntities(List<Board> boards) {

        return boards.stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }
}
