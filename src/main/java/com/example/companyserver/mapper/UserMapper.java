package com.example.companyserver.mapper;

import com.example.companyserver.config.CustomUserDetails;
import com.example.companyserver.dto.UserDto;
import com.example.companyserver.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToDto(UserEntity users);
    List<UserDto> usersToDto (List<UserEntity> users);

    UserEntity dtoToUser (UserDto userDto);
    List<UserEntity> dtoToUsers (List<UserDto> userDtos);

    CustomUserDetails fromUserEntityToCustomUserDetails(UserEntity user);
}
