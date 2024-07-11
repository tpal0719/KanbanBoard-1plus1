package com.sparta.springtrello.domain.board.controller;

import com.sparta.springtrello.auth.UserDetailsImpl;
import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.board.dto.BoardCreateRequestDto;
import com.sparta.springtrello.domain.board.dto.BoardResponseDto;
import com.sparta.springtrello.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    //보드생성
    @PostMapping
    public ResponseEntity<HttpResponseDto<Void>> createBoard(@RequestBody BoardCreateRequestDto requestDto,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.createBoard(requestDto,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.CREATED);
    }

    //보드 조회
    @GetMapping
    public  ResponseEntity<HttpResponseDto<List<BoardResponseDto>>> getBoards(){
        List<BoardResponseDto> boards = boardService.getBoards();
        return ResponseUtils.success(HttpStatus.OK, boards);
    }

    // 보드수정
    @PutMapping("/{boardId}")
    public ResponseEntity<HttpResponseDto<Void>> updateBoard(@PathVariable Long boardId,
                                                            @RequestBody BoardCreateRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.updateBoard(boardId,requestDto,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<HttpResponseDto<Void>> deleteBoard(@PathVariable Long boardId,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(boardId,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }






}
