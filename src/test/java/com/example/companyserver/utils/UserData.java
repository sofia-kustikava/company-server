package com.example.companyserver.utils;

import com.example.companyserver.dto.AuthDto;
import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.dto.UserDto;
import com.example.companyserver.entity.RoleEntity;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class UserData {
    public static UserEntity getCreatedUser() {
        return UserEntity.builder()
                .firstName("User")
                .lastName("Userovich")
                .email("user@mail.com")
                .password("user")
                .status(UserStatus.CREATED)
                .dateCreated(LocalDate.now())
                .updated(LocalDate.now())
                .roles(Arrays.asList(new RoleEntity(null, "USER", Collections.emptyList())))
                .build();
    }

    public static UserEntity getBlockedUser() {
        return UserEntity.builder()
                .id(1L)
                .firstName("User")
                .lastName("Userovich")
                .email("user@mail.com")
                .status(UserStatus.BANNED)
                .updated(LocalDate.now())
                .build();
    }

    public static UserDto getDtoUser() {
        return UserDto.builder()
                .id(1L)
                .firstName("User")
                .lastName("Userovich")
                .email("user@mail.com")
                .build();
    }

    public static RegisterDto getRegisterUser() {
        return RegisterDto.builder()
                .firstName("User")
                .lastName("Userovich")
                .email("user@mail.com")
                .password("user")
                .build();
    }

    public static AuthDto getAuthUser() {
        return AuthDto.builder()
                .email("user@mail.com")
                .build();
    }
}
