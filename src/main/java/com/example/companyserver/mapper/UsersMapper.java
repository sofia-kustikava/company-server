package com.example.companyserver.mapper;

import com.example.companyserver.dto.UsersDto;
import com.example.companyserver.entity.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    UsersDto userToDto(UsersEntity users);

    List<UsersDto> usersToDto (List<UsersEntity> users);

    UsersEntity dtoToUser (UsersDto userDto);
}
