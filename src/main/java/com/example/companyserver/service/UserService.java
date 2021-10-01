package com.example.companyserver.service;

import com.example.companyserver.dto.*;
import com.example.companyserver.entity.UsersEntity;
import com.example.companyserver.mapper.UsersMapper;
import com.example.companyserver.repo.UsersRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepo usersRepo;
    private final UsersMapper usersMapper;

    public UsersEntity findByEmail(String email) {
        return usersRepo.findByEmail(email).orElseThrow(() -> new RuntimeException(""));
    }

    public UsersDto findById(Long id) {
        UsersEntity user = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("There is no user with this email"));
        return usersMapper.INSTANCE.userToDto(user);
    }

}