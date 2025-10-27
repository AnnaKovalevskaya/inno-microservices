package com.innowise.demo.serserviceapp.mapper;

import com.innowise.demo.serserviceapp.dto.CardInfoDto;
import com.innowise.demo.serserviceapp.model.CardInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardInfoMapper {
    CardInfoDto toDto(CardInfo cardInfo);

    @Mapping(target = "user", ignore = true)
    CardInfo toEntity(CardInfoDto cardInfoDto);
}
