package com.example.companyserver.service;

import com.example.companyserver.dto.*;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.exceptions.UserNotFoundException;
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
        String variable = String.format("%s", email);
        return userRepo.findByEmail(email).orElseThrow(() -> new UserNotFoundException(variable));
    }

    public UserDto findById(Long id) {
        String variable = String.format("%s", id);
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(variable));
        return userMapper.INSTANCE.userToDto(user);
    }

    public void delete(Long id) {
        String variable = String.format("%s", id);
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(variable));
        user.setRoles(null);
        user.setSubscription(null);
        userRepo.save(user);
        userRepo.deleteById(id);
        log.info("User was deleted with this id: ", id);
    }

}