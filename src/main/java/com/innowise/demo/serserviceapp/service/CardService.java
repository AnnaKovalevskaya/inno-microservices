package com.innowise.demo.serserviceapp.service;

import com.innowise.demo.serserviceapp.dto.CardInfoDto;
import com.innowise.demo.serserviceapp.mapper.CardInfoMapper;
import com.innowise.demo.serserviceapp.model.CardInfo;
import com.innowise.demo.serserviceapp.model.User;
import com.innowise.demo.serserviceapp.repository.CardRepository;
import com.innowise.demo.serserviceapp.repository.UserRepository;
import com.innowise.demo.serserviceapp.exception.CardNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardInfoMapper cardInfoMapper;

    @Transactional
    @CacheEvict(value = "users", allEntries = true)  // Очищаем кэш пользователей при изменении карт
    public CardInfoDto createCard(CardInfoDto cardDto) {
        User user = userRepository.findById(cardDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        CardInfo card = cardInfoMapper.toEntity(cardDto);
        card.setUser(user);
        CardInfo savedCard = cardRepository.save(card);
        return cardInfoMapper.toDto(savedCard);
    }

    public Optional<CardInfoDto> getCardById(Long id) {
        CardInfo card = cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));
        return Optional.of(cardInfoMapper.toDto(card));
    }

    public Page<CardInfoDto> getAllCards(Pageable pageable) {
        return cardRepository.findAll(pageable).map(cardInfoMapper::toDto);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public CardInfoDto updateCard(Long id, CardInfoDto cardDto) {
        CardInfo card = cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));
        card.setNumber(cardDto.getNumber());
        card.setHolder(cardDto.getHolder());
        card.setExpirationDate(cardDto.getExpirationDate());
        if (!card.getUser().getId().equals(cardDto.getUserId())) {
            User newUser = userRepository.findById(cardDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            card.setUser(newUser);
        }
        CardInfo savedCard = cardRepository.save(card);
        return cardInfoMapper.toDto(savedCard);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void deleteCard(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new CardNotFoundException("Card not found with id: " + id);
        }
        cardRepository.deleteById(id);
    }
}