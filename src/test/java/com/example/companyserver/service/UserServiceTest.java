package com.example.companyserver.service;

import com.example.companyserver.dto.UserDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.exceptions.UserIsBannedException;
import com.example.companyserver.exceptions.UserIsUnbannedException;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.utils.TestingData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CompanyRepo companyRepo;

    @InjectMocks
    private UserService userService;

    private UserEntity user;
    private UserDto userWithDto;
    private UserEntity blockedUser;

    @BeforeEach
    public void beforeTest() {
        user = TestingData.getUser(1L, UserStatus.CREATED);
        userWithDto = TestingData.getDtoUser(1L);
        blockedUser = TestingData.getUser(1L, UserStatus.BANNED);
    }

    @Test
    public void findUserByIdTest() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.userToDto(user)).thenReturn(userWithDto);
        UserDto userFindId = userService.findById(user.getId());
        assertEquals(userWithDto, userFindId);
    }

    @Test
    public void deleteUserTest() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        CompanyEntity company1 = TestingData.getCompany("ONFA1");
        CompanyEntity company2 = TestingData.getCompany("ONFA2");
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        company1.setUsers(users);
        company2.setUsers(users);
        List<CompanyEntity> companies = new ArrayList<>(List.of(company1, company2));
        user.setCompanies(companies);

        userService.delete(user.getId());
        companies.forEach(company -> {
            List<UserEntity> userEntities = company.getUsers()
                    .stream()
                    .filter(user1 -> !user1.equals(user))
                    .collect(Collectors.toList());
            company.setUsers(userEntities);
            verify(companyRepo).save(company);
        });
        verify(userRepo).delete(user);
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
