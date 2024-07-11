package com.sparta.springtrello.domain.board.repository;

import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.exception.custom.board.BoardException;
import com.sparta.springtrello.common.ResponseCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardAdapter {
    private final BoardRepository boardRepository;

    public Board save(Board board) {
        return boardRepository.save(board);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardException(ResponseCodeEnum.BOARD_NOT_FOUND));
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public void delete(Board board) {
        boardRepository.delete(board);
    }
}
