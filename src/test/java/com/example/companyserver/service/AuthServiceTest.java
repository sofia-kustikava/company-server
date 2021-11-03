package com.example.companyserver.service;

import com.example.companyserver.dto.AuthDto;
import com.example.companyserver.dto.TokenDto;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.exceptions.IncorrectPasswordException;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.security.JwtProvider;
import com.example.companyserver.utils.TestingData;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    private UserEntity user;
    private AuthDto authUserWithDto;
    private AuthDto wrongAuthUserWithDto;

    @BeforeEach
    public void beforeTest() {
        user = TestingData.getUser(1L, UserStatus.CREATED);
        user.setPassword(passwordEncoder.encode("user"));
        authUserWithDto = TestingData.getAuthUser();
        authUserWithDto.setPassword(passwordEncoder.encode("user"));
    }

    @Test
    public void successfulAuthTest() {
        TokenDto token = new TokenDto("token");

        when(userRepo.findByEmail(authUserWithDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), authUserWithDto.getPassword())).thenReturn(true);
        when(jwtProvider.generateToken(user.getEmail())).thenReturn(token.getToken());
        TokenDto authToken = authService.auth(authUserWithDto);
        assertEquals(token, authToken);
    }

    @Test
    public void failedAuthTest() {
        wrongAuthUserWithDto = TestingData.getAuthUser();
        wrongAuthUserWithDto.setPassword(passwordEncoder.encode("admin"));

        when(userRepo.findByEmail(authUserWithDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), wrongAuthUserWithDto.getPassword())).thenReturn(false);
        Assert.assertThrows(IncorrectPasswordException.class, () -> authService.auth(wrongAuthUserWithDto));
    }
}
