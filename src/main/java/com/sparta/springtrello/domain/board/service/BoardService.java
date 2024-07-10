package com.sparta.springtrello.domain.board.service;

import com.sparta.springtrello.domain.board.dto.BoardCreateRequestDto;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.board.repository.BoardAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardAdapter boardAdapter;

    @Transactional
    public void createBoard(BoardCreateRequestDto requestDto) {
        Board board = Board.builder()
                .boardName(requestDto.getBoardName())
                .boardDescription(requestDto.getBoardDescription())
                .build();

        boardAdapter.save(board);
    }
}
