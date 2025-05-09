package com.example.subscription.mappers;

import org.mapstruct.Mapper;

import com.example.subscription.dto.UserDto;
import com.example.subscription.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User toUser(UserDto userDto);

  UserDto toUserDto(User user);
}
