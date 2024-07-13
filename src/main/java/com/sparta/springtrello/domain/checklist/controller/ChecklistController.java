package com.sparta.springtrello.domain.checklist.controller;

import com.sparta.springtrello.auth.UserDetailsImpl;
import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.checklist.dto.ChecklistCreateRequestDto;
import com.sparta.springtrello.domain.checklist.dto.ChecklistResponseDto;
import com.sparta.springtrello.domain.checklist.dto.ChecklistUpdateRequestDto;
import com.sparta.springtrello.domain.checklist.service.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ChecklistController {

    private final ChecklistService checklistService;

    // 체크리스트 추가
    @PostMapping("/cards/{cardId}/checklist")
    public ResponseEntity<HttpResponseDto<Void>> createChecklist(@PathVariable Long cardId,
                                                                 @RequestBody ChecklistCreateRequestDto requestDto,
                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checklistService.createChecklist(cardId,requestDto, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.CREATED);
    }

    // 체크리스트 조회(카드별 전체)
    @GetMapping("/cards/{cardId}/checklist")
    public ResponseEntity<HttpResponseDto<List<ChecklistResponseDto>>> getChecklistsByCard(@PathVariable Long cardId,
                                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ChecklistResponseDto> checklists = checklistService.getChecklistsByCard(cardId,userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK, checklists);
    }

    // 체크리스트 조회(단건)
    @GetMapping("/checklist/{checklistId}")
    public ResponseEntity<HttpResponseDto<ChecklistResponseDto>> getOneChecklist(@PathVariable Long checklistId,
                                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ChecklistResponseDto responseDto = checklistService.getOneChecklist(checklistId, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK, responseDto);
    }

    // 체크리스트 수정
    @PutMapping("/checklist/{checklistId}")
    public ResponseEntity<HttpResponseDto<Void>> updateChecklist(@PathVariable Long checklistId,
                                                                 @RequestBody ChecklistUpdateRequestDto requestDto,
                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checklistService.updateChecklist(checklistId, requestDto, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 체크리스트 삭제
    @DeleteMapping("/checklist/{checklistId}")
    public ResponseEntity<HttpResponseDto<Void>> deleteChecklist(@PathVariable Long checklistId,
                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checklistService.deleteChecklist(checklistId, userDetails.getUser());
        return ResponseUtils.success(HttpStatus.OK);
    }


}
