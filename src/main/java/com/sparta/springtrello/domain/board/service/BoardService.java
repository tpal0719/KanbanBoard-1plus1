package com.sparta.springtrello.domain.board.service;


import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.domain.board.dto.BoardCreateRequestDto;
import com.sparta.springtrello.domain.board.dto.BoardResponseDto;
import com.sparta.springtrello.domain.board.dto.BoardUpdateRequestDto;
import com.sparta.springtrello.domain.board.entity.Board;
import com.sparta.springtrello.domain.board.entity.BoardUser;
import com.sparta.springtrello.domain.board.repository.BoardRepository;
import com.sparta.springtrello.domain.board.repository.BoardUserRepository;
import com.sparta.springtrello.domain.user.entity.User;
import com.sparta.springtrello.domain.user.entity.UserRoleEnum;
import com.sparta.springtrello.domain.user.repository.UserAdapter;
import com.sparta.springtrello.domain.user.repository.UserRepository;
import com.sparta.springtrello.exception.custom.board.BoardException;
import com.sparta.springtrello.exception.custom.user.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardUserRepository boardUserRepository;
    private final UserRepository userRepository;


    @Transactional
    public void createBoard(BoardCreateRequestDto requestDto, User user) {

        validateBoardManager(user);

        Board board = Board.builder()
                .boardName(requestDto.getBoardName())
                .boardDescription(requestDto.getBoardDescription())
                .build();

        boardRepository.save(board);
    }

    // 보드 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards(User user) {
        List<Board> boards = boardRepository.findAll();
        if (boards.isEmpty()) {
            throw new BoardException(ResponseCodeEnum.BOARD_NOT_FOUND);
        }
        return BoardResponseDto.fromEntities(boards);
    }

    @Transactional(readOnly = true)
    public BoardResponseDto getOneBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardException(ResponseCodeEnum.BOARD_NOT_FOUND));
        return new BoardResponseDto(board);
    }

    // 보드 수정
    @Transactional
    public void updateBoard(Long boardId, BoardUpdateRequestDto requestDto, User user) {
        validateBoardManager(user);
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new BoardException(ResponseCodeEnum.BOARD_NOT_FOUND));
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
        validateBoardManager(user);
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new BoardException(ResponseCodeEnum.BOARD_NOT_FOUND));

        boardRepository.delete(board);
    }


    // 보드에 사용자 초대
    @Transactional
    public void inviteUserInBoard(Long boardId, Long userId, User user) {

        validateBoardManager(user);
        if(user.getId().equals(userId)){ //본인초대불가
            throw  new BoardException(ResponseCodeEnum.BOARD_INVITE_SELF_USER);
        }

        Board board = boardRepository.findById(boardId).orElseThrow(()-> new BoardException(ResponseCodeEnum.BOARD_NOT_FOUND));
        User inviteUser = userRepository.findById(userId).orElseThrow(()-> new UserException(ResponseCodeEnum.USER_NOT_FOUND));

        BoardUser boardUser = BoardUser.builder()
                .board(board)
                .user(inviteUser)
                .build();

        boardUserRepository.save(boardUser);
    }

    // 보드에 사용자 수락
    @Transactional
    public void inviteUserInBoardAccept(Long boardId,User user){
        BoardUser boardUser = boardUserRepository.findByBoardIdAndUserId(boardId,user.getId()).orElseThrow(
                ()-> new BoardException(ResponseCodeEnum.BOARD_NOT_FOUND));

        if(boardUser.isAccepted()){
            throw new BoardException(ResponseCodeEnum.BOARD_INVITE_ACCEPT);
        }
        else{
            boardUser.setAccepted(true);
        }
    }


    // 보드에 초대된 유저 삭제
    @Transactional
    public void deleteInviteUser(Long boardId,Long userId, User user) {

        validateBoardManager(user);

        BoardUser boardUser = boardUserRepository.findByBoardIdAndUserId(boardId,userId).orElseThrow(
                ()-> new BoardException(ResponseCodeEnum.BOARD_NOT_FOUND));

        if(boardUser.getUser().getId().equals(user.getId())){
            throw new BoardException(ResponseCodeEnum.BOARD_DELETE_SELF_USER);
        }

        if(!boardUser.isAccepted()){
            throw new BoardException(ResponseCodeEnum.BOARD_INVITE_NOT_ACCEPT);
        }


        boardUserRepository.delete(boardUser);

    }


    /* Utils */

    public void validateBoardManager(User user){
        if (!user.getUserRole().equals(UserRoleEnum.ROLE_MANAGER)) {
            throw new BoardException(ResponseCodeEnum.ACCESS_DENIED);
        }
    }


}
