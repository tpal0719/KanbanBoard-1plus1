package com.sparta.springtrello.domain.column.controller;

import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.column.dto.TaskColumnCreateRequestDto;
import com.sparta.springtrello.domain.column.service.TaskColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/columns")
public class TaskColumnController {

    private final TaskColumnService taskColumnService;

    @PostMapping("/boards/{boardId}")
    public ResponseEntity<HttpResponseDto<Void>> createTaskColumn(@PathVariable Long boardId, @RequestBody TaskColumnCreateRequestDto requestDto) {
        taskColumnService.createTaskColumn(boardId, requestDto);
        return ResponseUtils.success(HttpStatus.CREATED);
    }
}
