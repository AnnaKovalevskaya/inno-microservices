package com.innowise.demo.serserviceapp.mapper;

import com.innowise.demo.serserviceapp.dto.CardInfoDto;
import com.innowise.demo.serserviceapp.model.CardInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardInfoMapper {
    CardInfoDto toDto(CardInfo cardInfo);
    CardInfo toEntity(CardInfoDto cardInfoDto);
}