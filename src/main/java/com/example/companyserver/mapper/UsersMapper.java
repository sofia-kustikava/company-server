package com.example.companyserver.mapper;

import com.example.companyserver.dto.UsersDto;
import com.example.companyserver.entity.UsersEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UsersMapper {
    UsersDto userToDto(UsersEntity users);

    List<UsersDto> usersToDto (List<UsersEntity> users);

    UsersEntity dtoToUser (UsersDto userDto);
}
