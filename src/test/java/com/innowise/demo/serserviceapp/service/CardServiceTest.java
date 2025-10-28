package com.innowise.demo.serserviceapp.service;

import com.innowise.demo.serserviceapp.dto.CardInfoDto;
import com.innowise.demo.serserviceapp.exception.CardNotFoundException;
import com.innowise.demo.serserviceapp.mapper.CardInfoMapper;
import com.innowise.demo.serserviceapp.model.CardInfo;
import com.innowise.demo.serserviceapp.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardInfoMapper cardInfoMapper;

    @InjectMocks
    private CardService cardService;

    @Test
    void createCard_shouldReturnCreatedCard() {
        CardInfoDto cardDto = new CardInfoDto(null, "1234567890123456", "Anna Kovalevskaja", LocalDate.of(2001, 9, 19));
        CardInfo card = new CardInfo(1L, null, "1234567890123456", "Anna Kovalevskaja", LocalDate.of(2001, 9, 19));
        CardInfoDto expectedDto = new CardInfoDto(1L, "1234567890123456", "Anna Kovalevskaja", LocalDate.of(2001, 9, 19));

        when(cardInfoMapper.toEntity(cardDto)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);
        when(cardInfoMapper.toDto(card)).thenReturn(expectedDto);

        CardInfoDto result = cardService.createCard(cardDto);

        assertEquals(expectedDto, result);
        verify(cardRepository).save(card);
    }

    @Test
    void getCardById_shouldReturnCard() {
        CardInfo card = new CardInfo(1L, null, "1234567890123456", "Anna Kovalevskaja", LocalDate.of(2001, 9, 19));
        CardInfoDto cardDto = new CardInfoDto(1L, "1234567890123456", "Anna Kovalevskaja", LocalDate.of(2001, 9, 19));

        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardInfoMapper.toDto(card)).thenReturn(cardDto);

        Optional<CardInfoDto> result = cardService.getCardById(1L);

        assertTrue(result.isPresent());
        assertEquals(cardDto, result.get());
    }

    @Test
    void getCardById_shouldThrowExceptionWhenNotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> cardService.getCardById(1L));
    }

    @Test
    void getAllCards_shouldReturnPagedCards() {
        Pageable pageable = PageRequest.of(0, 10);
        CardInfo card = new CardInfo(1L, null, "1234567890123456", "Anna Kovalevskaja", LocalDate.of(2001, 9, 19));
        Page<CardInfo> cardPage = new PageImpl<>(List.of(card), pageable, 1);
        CardInfoDto cardDto = new CardInfoDto(1L, "1234567890123456", "Anna Kovalevskaja", LocalDate.of(2001, 9, 19));
        Page<CardInfoDto> expectedPage = new PageImpl<>(List.of(cardDto), pageable, 1);

        when(cardRepository.findAll(pageable)).thenReturn(cardPage);
        when(cardInfoMapper.toDto(card)).thenReturn(cardDto);

        Page<CardInfoDto> result = cardService.getAllCards(pageable);

        assertEquals(expectedPage.getContent(), result.getContent());
    }

    @Test
    void deleteCard_shouldDeleteCard() {
        when(cardRepository.existsById(1L)).thenReturn(true);

        cardService.deleteCard(1L);

        verify(cardRepository).deleteById(1L);
    }

    @Test
    void deleteCard_shouldThrowExceptionWhenNotFound() {
        when(cardRepository.existsById(1L)).thenReturn(false);

        assertThrows(CardNotFoundException.class, () -> cardService.deleteCard(1L));
    }
}
