package com.innowise.demo.serserviceapp.controller;

import com.innowise.demo.serserviceapp.model.CardInfo;
import com.innowise.demo.serserviceapp.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @PostMapping
    public CardInfo createCard(@RequestBody CardInfo card) {
        return cardService.createCard(card);
    }

    @GetMapping("/{id}")
    public Optional<CardInfo> getCardById(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    @GetMapping
    public Page<CardInfo> getAllCards(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cardService.getAllCards(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }
}
