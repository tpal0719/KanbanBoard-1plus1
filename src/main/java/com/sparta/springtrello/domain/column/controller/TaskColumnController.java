package com.sparta.springtrello.domain.column.controller;

import com.sparta.springtrello.auth.UserDetailsImpl;
import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.column.dto.TaskColumnCreateRequestDto;
import com.sparta.springtrello.domain.column.dto.TaskColumnResponseDto;
import com.sparta.springtrello.domain.column.dto.TaskColumnUpdateOrderRequestDto;
import com.sparta.springtrello.domain.column.dto.TaskColumnUpdateRequestDto;
import com.sparta.springtrello.domain.column.service.TaskColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class TaskColumnController {

    private final TaskColumnService taskColumnService;

    // 컬럼 생성
    @PostMapping("/boards/{boardId}/columns")
    public ResponseEntity<HttpResponseDto<Void>> createTaskColumn(
            @PathVariable Long boardId,
            @RequestBody TaskColumnCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        taskColumnService.createTaskColumn(boardId, requestDto, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.CREATED);
    }

    // 컬럼 조회(전체)
    @GetMapping("/boards/{boardId}/columns")
    public ResponseEntity<HttpResponseDto<List<TaskColumnResponseDto>>> getTaskColumns(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<TaskColumnResponseDto> columns = taskColumnService.getTaskColumns(boardId, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK, columns);
    }

    // 컬럼 조회(단건)
    @GetMapping("/columns/{columnId}")
    public ResponseEntity<HttpResponseDto<TaskColumnResponseDto>> getOneTaskColumn(
            @PathVariable Long columnId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TaskColumnResponseDto columns = taskColumnService.getOneTaskColumn(columnId, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK, columns);
    }

    // 컬럼 수정
    @PutMapping("/columns/{columnId}")
    public ResponseEntity<HttpResponseDto<Void>> updateTaskColumn(
            @PathVariable Long columnId,
            @RequestBody TaskColumnUpdateRequestDto taskColumnUpdateRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        taskColumnService.updateTaskColumn(columnId, taskColumnUpdateRequestDto, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 컬럼 순서 변경
    @PutMapping("/columns/{columnId}/order")
    public ResponseEntity<HttpResponseDto<Void>> updateTaskColumnOrder(
            @PathVariable Long columnId,
            @RequestBody TaskColumnUpdateOrderRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        taskColumnService.updateTaskColumnOrder(columnId, requestDto.getColumnOrder(), userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 컬럼 삭제
    @DeleteMapping("/columns/{columnId}")
    public ResponseEntity<HttpResponseDto<Void>> deleteTaskColumn(
            @PathVariable Long columnId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        taskColumnService.deleteTaskColumn(columnId, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }
}