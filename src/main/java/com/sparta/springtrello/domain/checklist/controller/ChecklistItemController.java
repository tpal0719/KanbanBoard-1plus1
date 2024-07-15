package com.sparta.springtrello.domain.checklist.controller;

import com.sparta.springtrello.auth.UserDetailsImpl;
import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.checklist.dto.*;
import com.sparta.springtrello.domain.checklist.service.ChecklistItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ChecklistItemController {

    private final ChecklistItemService checklistItemService;

    // 체크리스트 항목 추가
    @PostMapping("/checklists/{checklistId}/checklist-item")
    public ResponseEntity<HttpResponseDto<Void>> createChecklistItem(@PathVariable Long checklistId,
                                                                 @RequestBody ChecklistItemCreateRequestDto requestDto,
                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checklistItemService.createChecklistItem(checklistId,requestDto,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.CREATED);
    }

    // 체크리스트 항목 조회(단건)
    @GetMapping("/checklist-item/{checklistItemId}")
    public ResponseEntity<HttpResponseDto<ChecklistItemResponseDto>> getChecklistItem(@PathVariable Long checklistItemId,
                                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChecklistItemResponseDto responseDto = checklistItemService.getChecklistItem(checklistItemId,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK,responseDto);
    }

    // 체크리스트 항목 수정
    @PutMapping("/checklist-item/{checklistItemId}")
    public ResponseEntity<HttpResponseDto<Void>> updateChecklistItem(@PathVariable Long checklistItemId,
                                                                 @RequestBody ChecklistItemUpdateRequestDto requestDto,
                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checklistItemService.updateChecklistItem(checklistItemId,requestDto,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }


    // 체크리스트 항목 수정 - 완료 토글
    @PutMapping("/checklist-item/{checklistItemId}/toggle")
    public ResponseEntity<HttpResponseDto<Void>> toggleChecklistItem(@PathVariable Long checklistItemId,
                                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checklistItemService.toggleChecklistItem(checklistItemId,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 체크리스트 항목 삭제
    @DeleteMapping("/checklist-item/{checklistItemId}")
    public ResponseEntity<HttpResponseDto<Void>> deleteChecklistItem(@PathVariable Long checklistItemId,
                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checklistItemService.deleteChecklistItem(checklistItemId,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }
}
