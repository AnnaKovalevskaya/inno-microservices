package com.innowise.demo.serserviceapp.mapper;

import com.innowise.demo.serserviceapp.dto.UserDto;
import com.innowise.demo.serserviceapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(target = "cards", ignore = true)
    User toEntity(UserDto userDto);
}
