package com.sparta.springtrello.domain.board.service;


import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.domain.board.dto.BoardCreateRequestDto;
import com.sparta.springtrello.domain.board.dto.BoardResponseDto;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.board.entity.BoardUser;
import com.sparta.springtrello.domain.board.repository.BoardAdapter;
import com.sparta.springtrello.domain.board.repository.BoardUserAdapter;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.entity.UserRoleEnum;
import com.sparta.springtrello.domain.user.repository.UserAdapter;
import com.sparta.springtrello.exception.custom.board.BoardException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardAdapter boardAdapter;
    private final UserAdapter userAdapter;
    private final BoardUserAdapter boardUserAdapter;

    @Transactional
    public void createBoard(BoardCreateRequestDto requestDto, User user) {

        if (!user.getUserRole().equals(UserRoleEnum.ROLE_MANAGER)) {
            throw new BoardException(ResponseCodeEnum.ACCESS_DENIED);
        }

        Board board = Board.builder()
                .boardName(requestDto.getBoardName())
                .boardDescription(requestDto.getBoardDescription())
                .build();

        boardAdapter.save(board);
    }

    // 보드 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards(User user) {
//        List<Board> boards = boardAdapter.findAllbyUserId(user.getId());
        List<Board> boards = boardAdapter.findAll();
        if (boards.isEmpty()) {
            throw new BoardException(ResponseCodeEnum.BOARD_NOT_FOUND);
        }
        return BoardResponseDto.fromEntities(boards);
    }

    @Transactional(readOnly = true)
    public BoardResponseDto getOneBoard(Long boardId) {
        Board board = boardAdapter.findById(boardId);

        return new BoardResponseDto(board);
    }

    // 보드 수정
    @Transactional
    public void updateBoard(Long boardId, BoardCreateRequestDto requestDto, User user) {
        if (!user.getUserRole().equals(UserRoleEnum.ROLE_MANAGER)) {
            throw new BoardException(ResponseCodeEnum.ACCESS_DENIED);
        }
        Board board = boardAdapter.findById(boardId);
        if (requestDto.getBoardName() != null) {
            board.setBoardName(requestDto.getBoardName());
        }
        if (requestDto.getBoardDescription() != null) {
            board.setBoardDescription(requestDto.getBoardDescription());
        }
    }

    // 보드 삭제
    @Transactional
    public void deleteBoard(Long boardId, User user) {
        if (!user.getUserRole().equals(UserRoleEnum.ROLE_MANAGER)) {
            throw new BoardException(ResponseCodeEnum.ACCESS_DENIED);
        }
        Board board = boardAdapter.findById(boardId);

        boardAdapter.delete(board);
    }


    // 보드에 사용자 초대
    public void inviteUserInBoard(Long boardId, Long userId, User user) {

        if (!user.getUserRole().equals(UserRoleEnum.ROLE_MANAGER)) {
            throw new BoardException(ResponseCodeEnum.ACCESS_DENIED);
        }
        Board board = boardAdapter.findById(boardId);
        User inviteUser = userAdapter.findById(userId);

        BoardUser boardUser = BoardUser.builder()
                .board(board)
                .user(inviteUser)
                .build();

        boardUserAdapter.save(boardUser);
    }

}
