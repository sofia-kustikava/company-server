package com.example.companyserver.service;

import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.entity.RoleEntity;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.exceptions.UserAlreadyExistException;
import com.example.companyserver.repo.RoleRepo;
import com.example.companyserver.repo.UserRepo;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private RoleRepo roleRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    @InjectMocks
    private RegisterService registerService;

    private UserEntity user;
    private RegisterDto registerExistUser;
    private RoleEntity role;

    @BeforeEach
    public void beforeTest() {
        user = UserEntity.builder()
                .firstName("User")
                .lastName("Userovich")
                .email("user1@mail.com")
                .password(passwordEncoder.encode("user"))
                .status(UserStatus.CREATED)
                .dateCreated(LocalDate.now())
                .updated(LocalDate.now())
                .build();
        role = roleRepo.findByRoleName("USER");
        user.setRoles(Arrays.asList(role));

        registerExistUser = RegisterDto.builder()
                .firstName("User")
                .lastName("Userovich")
                .email("user1@mail.com")
                .password(passwordEncoder.encode("user"))
                .build();
    }

    @Test
    public void registerUserTest() {
        when(userRepo.findByEmail(registerExistUser.getEmail())).thenReturn(Optional.of(user));
        when(roleRepo.findByRoleName("USER")).thenReturn(role);
        Mockito.verify(mailService).sendEmailRegistration(registerExistUser);
        Mockito.verify(userRepo).save(user);
    }

    @Test
    public void registerFailedUserTest() {
        when(userRepo.findByEmail(registerExistUser.getEmail())).thenReturn(Optional.of(user));
        when(userRepo.findByEmail(registerExistUser.getEmail()).isPresent()).thenReturn(true);
        Assert.assertThrows(UserAlreadyExistException.class, () -> registerService.registerUser(registerExistUser));
    }
}
