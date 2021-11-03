package com.example.companyserver.service;

import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.exceptions.InvalidUserParameterException;
import com.example.companyserver.exceptions.UserAlreadyExistException;
import com.example.companyserver.repo.RoleRepo;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.utils.TestingData;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
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
    private RegisterDto registerUser;

    @BeforeEach
    public void beforeTest() {
        user = TestingData.getUser(null, UserStatus.CREATED);
        registerUser = TestingData.getRegisterUser();
    }

    @Test
    public void registerUserTest() throws InvalidUserParameterException {
        when(userRepo.findByEmail(registerUser.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerUser.getPassword())).thenReturn(registerUser.getPassword());
        when(roleRepo.findByRoleName("USER")).thenReturn(user.getRoles().get(0));

        registerService.registerUser(registerUser);

        verify(mailService).sendEmailRegistration(registerUser);
        verify(userRepo).save(refEq(user));
    }

    @Test
    public void registerFailedUserTest() {
        when(userRepo.findByEmail(registerUser.getEmail())).thenReturn(Optional.of(UserEntity.builder().email("user@mail.com").build()));
        Assert.assertThrows(UserAlreadyExistException.class, () -> registerService.registerUser(registerUser));
    }
}
