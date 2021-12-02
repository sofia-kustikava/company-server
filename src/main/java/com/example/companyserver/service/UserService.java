package com.example.companyserver.service;

import com.example.companyserver.dto.*;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.exceptions.UserIsBannedException;
import com.example.companyserver.exceptions.UserIsUnbannedException;
import com.example.companyserver.exceptions.UserNotFoundException;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    public UserDto findById(Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("%s", id)));
        return userMapper.userToDto(user);
    }

    public Long findByIdUser(HttpServletRequest request) {
        Long userId = authenticationService.getAuthUserId(request);
        return userId;
    }

    public void delete(Long id) {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("%s", id)));
        user.setRoles(null);
        user.setCompanies(null);
        userRepo.delete(user);
        log.info("User was deleted with this id: {}", id);
    }

    public void blockUser(Long userId) {
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("%s", userId)));
        if (!user.getStatus().equals(UserStatus.BANNED)) {
            user.setStatus(UserStatus.BANNED);
            userRepo.save(user);
        } else {
            log.info("This user is already banned: {}", user.getEmail());
            throw new UserIsBannedException(String.format("%s", user.getEmail()));
        }
    }

    public void unblockUser(Long userId) {
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("%s", userId)));
        if (user.getStatus().equals(UserStatus.BANNED)) {
            user.setStatus(UserStatus.CREATED);
            userRepo.save(user);
        } else {
            log.info("This user is already unbanned: {}", user.getEmail());
            throw new UserIsUnbannedException(String.format("%s", user.getEmail()));
        }
    }
}