package com.innowise.demo.serserviceapp.mapper;

import com.innowise.demo.serserviceapp.dto.CardInfoDto;
import com.innowise.demo.serserviceapp.model.CardInfo;
import com.innowise.demo.serserviceapp.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardInfoMapper {
    CardInfoDto toDto(CardInfo cardInfo);
    CardInfo toEntity(CardInfoDto cardInfoDto);

    default User map(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }

    default Long map(User user) {
        return user != null ? user.getId() : null;
    }
}