package com.example.companyserver.service;

import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.entity.RoleEntity;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.exceptions.InvalidUserParameterException;
import com.example.companyserver.exceptions.UserAlreadyExistException;
import com.example.companyserver.repo.RoleRepo;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public void registerUser(RegisterDto registerDto) throws InvalidUserParameterException {
        if (emailExist(registerDto.getEmail())) throw new UserAlreadyExistException(String.format("%s", registerDto.getEmail()));

        try {
            UserEntity user = UserEntity.builder()
                    .firstName(registerDto.getFirstName())
                    .lastName(registerDto.getLastName())
                    .email(registerDto.getEmail())
                    .password(passwordEncoder.encode(registerDto.getPassword()))
                    .status(UserStatus.CREATED)
                    .dateCreated(LocalDate.now())
                    .updated(LocalDate.now())
                    .build();

            RoleEntity userRole = roleRepo.findByRoleName("USER");
            user.setRoles(Arrays.asList(userRole));

            mailService.sendEmailRegistration(registerDto);
            userRepo.save(user);
        } catch (Exception e) {
            throw new InvalidUserParameterException(e.getMessage());
        }

    }

    private boolean emailExist(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

}
