package com.sparta.springtrello.domain.board.controller;

import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.board.dto.BoardCreateRequestDto;
import com.sparta.springtrello.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<HttpResponseDto<Void>> createBoard(@RequestBody BoardCreateRequestDto requestDto) {
        boardService.createBoard(requestDto);
        return ResponseUtils.success(HttpStatus.CREATED);
    }
}
