package com.sparta.springtrello.domain.card.controller;

import com.sparta.springtrello.common.HttpResponseDto;
import com.sparta.springtrello.common.ResponseUtils;
import com.sparta.springtrello.domain.card.dto.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    @PostMapping("/columns/{columnId}")
    public ResponseEntity<HttpResponseDto<Void>> createCard(
            @PathVariable Long columnId,
            @Validated @RequestBody CardCreateRequestDto requestDto) {
        cardService.createCard(columnId, requestDto);
        return ResponseUtils.success(HttpStatus.CREATED);
    }
}
