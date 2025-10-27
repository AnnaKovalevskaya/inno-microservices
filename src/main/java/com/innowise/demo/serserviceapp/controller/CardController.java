package com.innowise.demo.serserviceapp.controller;

import com.innowise.demo.serserviceapp.dto.CardInfoDto;
import com.innowise.demo.serserviceapp.service.CardService;
import com.innowise.demo.serserviceapp.exception.CardNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<CardInfoDto> createCard(@Valid @RequestBody CardInfoDto cardDto) {
        CardInfoDto createdCard = cardService.createCard(cardDto);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardInfoDto> getCardById(@PathVariable Long id) {
        CardInfoDto card = cardService.getCardById(id).orElseThrow(() -> new CardNotFoundException("Card not found"));
        return ResponseEntity.ok(card);
    }

    @GetMapping
    public ResponseEntity<Page<CardInfoDto>> getAllCards(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CardInfoDto> cards = cardService.getAllCards(pageable);
        return ResponseEntity.ok(cards);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
