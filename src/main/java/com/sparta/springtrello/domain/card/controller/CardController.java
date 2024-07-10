package com.sparta.springtrello.domain.card.controller;

import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.card.dto.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.dto.CardResponseDto;
import com.sparta.springtrello.domain.card.dto.CardUpdateRequestDto;
import com.sparta.springtrello.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    // 카드 생성
    @PostMapping("/columns/{columnId}")
    public ResponseEntity<HttpResponseDto<Void>> createCard(@PathVariable Long columnId, @RequestBody CardCreateRequestDto requestDto) {
        cardService.createCard(columnId, requestDto);
        return ResponseUtils.success(HttpStatus.CREATED);
    }

    // 카드 조회(보드별)
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<HttpResponseDto<List<CardResponseDto>>> getCardsByBoardId(@PathVariable Long boardId) {
        List<CardResponseDto> cards = cardService.getCardsByBoardId(boardId);
        return ResponseUtils.success(HttpStatus.OK, cards);
    }

    // 카드 조회(칼럼별)
    @GetMapping("/columns/{columnId}")
    public ResponseEntity<HttpResponseDto<List<CardResponseDto>>> getCardsByColumnId(@PathVariable Long columnId) {
        List<CardResponseDto> cards = cardService.getCardsByColumnId(columnId);
        return ResponseUtils.success(HttpStatus.OK, cards);
    }

    // 카드 조회(유저별)
    @GetMapping("/users/{userId}")
    public ResponseEntity<HttpResponseDto<List<CardResponseDto>>> getCardsByUserId(@PathVariable Long userId) {
        List<CardResponseDto> cards = cardService.getCardsByUserId(userId);
        return ResponseUtils.success(HttpStatus.OK, cards);
    }

    // 카드 작업자 할당
    @PostMapping("/{cardId}/assign/{userId}")
    public ResponseEntity<HttpResponseDto<Void>> addCardMember(
            @PathVariable Long cardId,
            @PathVariable Long userId) {
        cardService.addCardMember(cardId, userId);
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 카드 상세 수정
    @PutMapping("/{cardId}")
    public ResponseEntity<HttpResponseDto<Void>> updateCard(
            @PathVariable Long cardId,
            @RequestBody CardUpdateRequestDto requestDto) {
        cardService.updateCard(cardId, requestDto);
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 카드 순서 조정
    @PutMapping("/{cardId}/order")
    public ResponseEntity<HttpResponseDto<Void>> updateCardOrder(
            @PathVariable Long cardId,
            @RequestBody CardUpdateRequestDto requestDto) {
        cardService.updateCardOrder(cardId, requestDto);
        return ResponseUtils.success(HttpStatus.OK);
    }

    // 카드 삭제
    @DeleteMapping("/{cardId}")
    public ResponseEntity<HttpResponseDto<Void>> deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return ResponseUtils.success(HttpStatus.OK);
    }
}
