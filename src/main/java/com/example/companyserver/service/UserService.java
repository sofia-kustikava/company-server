package com.example.companyserver.service;

import com.example.companyserver.entity.UsersEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<UsersEntity> getUsers();
    UsersEntity findById(Long id);
    Optional<UsersEntity> findByEmail(String email);

    UsersEntity register(UsersEntity user);
    void saveRegisteredUser(UsersEntity user);

    void  delete(Long id);
}