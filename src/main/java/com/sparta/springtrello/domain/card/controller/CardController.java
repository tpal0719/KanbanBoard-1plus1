package com.sparta.springtrello.domain.card.controller;

import com.sparta.springtrello.auth.UserDetailsImpl;
import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.card.dto.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.dto.CardResponseDto;
import com.sparta.springtrello.domain.card.dto.CardUpdateRequestDto;
import com.sparta.springtrello.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CardController {

    private final CardService cardService;

    // 카드 생성
    @PostMapping("/columns/{columnId}/cards")
    public ResponseEntity<HttpResponseDto<Void>> createCard(
            @PathVariable Long columnId,
            @RequestBody CardCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.createCard(columnId, requestDto, userDetails.getUser().getId());
        return ResponseUtils.success(HttpStatus.CREATED);
    }


    // 카드 조회(보드별)
    @GetMapping("/boards/{boardId}/cards")
    public ResponseEntity<HttpResponseDto<List<CardResponseDto>>> getCardsByBoardId(@PathVariable Long boardId) {
        List<CardResponseDto> cards = cardService.getCardsByBoardId(boardId);
        return ResponseUtils.success(HttpStatus.OK, cards);
    }

    // 카드 조회(칼럼별)
    @GetMapping("/columns/{columnId}/cards")
    public ResponseEntity<HttpResponseDto<List<CardResponseDto>>> getCardsByColumnId(@PathVariable Long columnId) {
        List<CardResponseDto> cards = cardService.getCardsByColumnId(columnId);
        return ResponseUtils.success(HttpStatus.OK, cards);
    }

    // 카드 조회(유저별)
    @GetMapping("/users/{userId}/cards")
    public ResponseEntity<HttpResponseDto<List<CardResponseDto>>> getCardsByUserId(@PathVariable Long userId) {
        List<CardResponseDto> cards = cardService.getCardsByUserId(userId);
        return ResponseUtils.success(HttpStatus.OK, cards);
    }

    // 카드 작업자 할당
    @PostMapping("/cards/{cardId}/assign/{userId}")
    public ResponseEntity<HttpResponseDto<Void>> addCardMember(
            @PathVariable Long cardId,
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.addCardMember(cardId, userId, userDetails.getUser().getId());
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 카드 상세 수정
    @PutMapping("/cards/{cardId}")
    public ResponseEntity<HttpResponseDto<Void>> updateCard(
            @PathVariable Long cardId,
            @RequestBody CardUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.updateCard(cardId, requestDto, userDetails.getUser().getId());
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 카드 순서 조정
    @PutMapping("/cards/{cardId}/order")
    public ResponseEntity<HttpResponseDto<Void>> updateCardOrder(
            @PathVariable Long cardId,
            @RequestBody CardUpdateRequestDto requestDto) {
        cardService.updateCardOrder(cardId, requestDto);
        return ResponseUtils.success(HttpStatus.OK);
    }


    // 파일 업로드
    @PostMapping("/cards/{cardId}/files")
    public ResponseEntity<HttpResponseDto<Void>> uploadFile(
            @PathVariable Long cardId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "fileDescription", required = false) String fileDescription,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.uploadFileAttachment(cardId, file, fileDescription, userDetails.getUser().getId());
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 파일 삭제
    @DeleteMapping("/cards/files/{fileAttachmentId}")
    public ResponseEntity<HttpResponseDto<Void>> deleteFile(
            @PathVariable Long fileAttachmentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.deleteFileAttachment(fileAttachmentId, userDetails.getUser().getId());
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 파일 다운로드
    @GetMapping("/cards/files/{fileAttachmentId}/download")
    public ResponseEntity<Resource> downloadFileAttachment(
            @PathVariable Long fileAttachmentId) {
        Resource resource = cardService.downloadFileAttachment(fileAttachmentId);
        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    // 카드 삭제
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<HttpResponseDto<Void>> deleteCard(
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.deleteCard(cardId, userDetails.getUser().getId());
        return ResponseUtils.success(HttpStatus.OK);
    }
}
