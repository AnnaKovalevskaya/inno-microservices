package com.innowise.demo.serserviceapp.service;

import com.innowise.demo.serserviceapp.model.CardInfo;
import com.innowise.demo.serserviceapp.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public CardInfo createCard(CardInfo card) {
        return cardRepository.save(card);
    }

    public Optional<CardInfo> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    public Page<CardInfo> getAllCards(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}