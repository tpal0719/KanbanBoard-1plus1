package com.sparta.springtrello.domain.board.repository;

import com.sparta.springtrello.domain.board.entity.BoardUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardUserAdapter {
    private final BoardUserRepository boardUserRepository;

    public void save(BoardUser boardUser) {
        boardUserRepository.save(boardUser);
    }
}
