package com.example.companyserver.service;

import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.entity.RoleEntity;
import com.example.companyserver.entity.Status;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.repo.RoleRepo;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RegisterService {


    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDto registerDto) {
        UserEntity user = UserEntity.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .status(Status.ACTIVE)
                .dateCreated(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
        RoleEntity userRole = roleRepo.findByRoleName("USER");
        user.setRoles(new ArrayList<>(Collections.singletonList(userRole)));

        userRepo.save(user);
    }
}
