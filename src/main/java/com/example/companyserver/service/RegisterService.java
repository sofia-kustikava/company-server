package com.example.companyserver.service;

import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.entity.RolesEntity;
import com.example.companyserver.entity.Status;
import com.example.companyserver.entity.UsersEntity;
import com.example.companyserver.repo.RolesRepo;
import com.example.companyserver.repo.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class RegisterService {


    private final UsersRepo usersRepo;
    private final RolesRepo rolesRepo;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDto registerDto) {
        UsersEntity user = UsersEntity.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .status(Status.ACTIVE)
                .dateCreated(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
        RolesEntity userRole = rolesRepo.findByRoleName("USER");
        user.setRoles(new ArrayList<>(Arrays.asList(userRole)));

        usersRepo.save(user);
    }
}
