package com.innowise.demo.serserviceapp.service;

import com.innowise.demo.serserviceapp.dto.CardInfoDto;
import com.innowise.demo.serserviceapp.mapper.CardInfoMapper;
import com.innowise.demo.serserviceapp.model.CardInfo;
import com.innowise.demo.serserviceapp.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardInfoMapper cardInfoMapper;

    public CardInfoDto createCard(CardInfoDto cardDto) {
        CardInfo card = cardInfoMapper.toEntity(cardDto);
        CardInfo savedCard = cardRepository.save(card);
        return cardInfoMapper.toDto(savedCard);
    }

    public Optional<CardInfoDto> getCardById(Long id) {
        return cardRepository.findById(id).map(cardInfoMapper::toDto);
    }

    public Page<CardInfoDto> getAllCards(Pageable pageable) {
        return cardRepository.findAll(pageable).map(cardInfoMapper::toDto);
    }

    @Transactional
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}