package com.sparta.springtrello.domain.column.controller;

import com.sparta.springtrello.auth.UserDetailsImpl;
import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.column.dto.TaskColumnCreateRequestDto;
import com.sparta.springtrello.domain.column.dto.TaskColumnResponseDto;
import com.sparta.springtrello.domain.column.dto.TaskColumnUpdateOrderRequestDto;
import com.sparta.springtrello.domain.column.service.TaskColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/columns")
public class TaskColumnController {

    private final TaskColumnService taskColumnService;

    // 컬럼 생성
    @PostMapping("/boards/{boardId}")
    public ResponseEntity<HttpResponseDto<Void>> createTaskColumn(
            @PathVariable Long boardId,
            @RequestBody TaskColumnCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        taskColumnService.createTaskColumn(boardId, requestDto, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.CREATED);
    }

    // 컬럼 조회
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<HttpResponseDto<List<TaskColumnResponseDto>>> getTaskColumns(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<TaskColumnResponseDto> columns = taskColumnService.getTaskColumns(boardId);
        return ResponseUtils.success(HttpStatus.OK, columns);
    }

    // 컬럼 순서 변경
    @PutMapping("/boards/{boardId}/order")
    public ResponseEntity<HttpResponseDto<Void>> updateTaskColumnOrder(
            @PathVariable Long boardId,
            @RequestBody TaskColumnUpdateOrderRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        taskColumnService.updateTaskColumnOrder(boardId, requestDto, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 컬럼 삭제
    @DeleteMapping("/{columnId}")
    public ResponseEntity<HttpResponseDto<Void>> deleteTaskColumn(
            @PathVariable Long columnId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        taskColumnService.deleteTaskColumn(columnId, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }
}