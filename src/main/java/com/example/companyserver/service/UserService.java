package com.example.companyserver.service;

import com.example.companyserver.dto.*;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public UserEntity findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("There is no user with this email"));
    }

    public UserDto findById(Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("There is no user with this id"));
        return userMapper.INSTANCE.userToDto(user);
    }

    public void delete(Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("There is no user with this id"));
        user.setRoles(null);
        userRepo.save(user);
        userRepo.deleteById(id);
        log.info("User was deleted with this id: ", id);
    }

}