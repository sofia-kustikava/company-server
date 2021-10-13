package com.example.companyserver.service;

import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.entity.RoleEntity;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.exceptions.InvalidUserParameterException;
import com.example.companyserver.repo.RoleRepo;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDto registerDto) throws InvalidUserParameterException {
        try {
            UserEntity user = UserEntity.builder()
                    .firstName(registerDto.getFirstName())
                    .lastName(registerDto.getLastName())
                    .email(registerDto.getEmail())
                    .password(passwordEncoder.encode(registerDto.getPassword()))
                    .status(UserStatus.CREATED)
                    .dateCreated(LocalDateTime.now())
                    .updated(LocalDateTime.now())
                    .build();
            RoleEntity userRole = roleRepo.findByRoleName("USER");
            user.setRoles(Arrays.asList(userRole));

            userRepo.save(user);
        } catch (Exception e) {
            throw new InvalidUserParameterException(e.getMessage());
        }

    }
}
