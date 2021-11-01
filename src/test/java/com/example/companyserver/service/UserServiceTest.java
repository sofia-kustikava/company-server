package com.example.companyserver.service;

import com.example.companyserver.dto.UserDto;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.exceptions.UserIsBannedException;
import com.example.companyserver.exceptions.UserIsUnbannedException;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.utils.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserEntity user;
    private UserDto userWithDto;
    private UserEntity blockedUser;

    @BeforeEach
    public void beforeTest() {
        user = UserData.getCreatedUser();
        userWithDto = UserData.getDtoUser();
        blockedUser = UserData.getBlockedUser();
    }

    @Test
    public void findUserByEmailTest() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userMapper.userToDto(user)).thenReturn(userWithDto);
        UserDto userFindEmail = userService.findByEmail(user.getEmail());
        assertEquals(userWithDto, userFindEmail);
    }

    @Test
    public void findUserByIdTest() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.userToDto(user)).thenReturn(userWithDto);
        UserDto userFindId = userService.findById(user.getId());
        assertEquals(userWithDto, userFindId);
    }

    @Test
    public void findUserByEntityEmailTest() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserEntity actual = userService.findEntityByEmail(user.getEmail());
        assertEquals(user, actual);
    }

    @Test
    public void deleteUserTest() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        userService.delete(user.getId());
    }

    @Test
    public void blockUserTest() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        userService.blockUser(user.getId());
        Mockito.verify(userRepo).save(user);
    }

    @Test
    public void unblockUserTest() {
        when(userRepo.findById(blockedUser.getId())).thenReturn(Optional.of(blockedUser));
        userService.unblockUser(blockedUser.getId());
        Mockito.verify(userRepo).save(blockedUser);
    }

    @Test
    public void failedBlockUserTest() {
        when(userRepo.findById(blockedUser.getId())).thenReturn(Optional.of(blockedUser));
        assertThrows(UserIsBannedException.class, () -> userService.blockUser(blockedUser.getId()));
    }

    @Test
    public void failedUnblockUserTest() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        assertThrows(UserIsUnbannedException.class, () -> userService.unblockUser(user.getId()));
    }
}
