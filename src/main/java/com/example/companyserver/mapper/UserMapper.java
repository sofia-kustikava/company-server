package com.example.companyserver.mapper;

import com.example.companyserver.config.CustomUserDetails;
import com.example.companyserver.dto.AuthDto;
import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.dto.UserDto;
import com.example.companyserver.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToDto(UserEntity user);
    List<UserDto> usersToDto (List<UserEntity> users);

    UserEntity dtoToUser (UserDto userDto);
    List<UserEntity> dtoToUsers (List<UserDto> userDtos);

    CustomUserDetails fromUserDtoToCustomUserDetails(UserDto user);

    RegisterDto registerUserToDto (UserEntity user);
    RegisterDto dtoToRegisterUser (RegisterDto userDto);

    AuthDto authUserToDto(UserEntity user);
    UserEntity userToAuthDto(AuthDto authDto);
}
