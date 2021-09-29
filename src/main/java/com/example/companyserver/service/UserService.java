package com.example.companyserver.service;

import com.example.companyserver.dto.UsersDto;
import com.example.companyserver.entity.RolesEntity;
import com.example.companyserver.entity.Status;
import com.example.companyserver.entity.UsersEntity;
import com.example.companyserver.error.UserAlreadyExistException;
import com.example.companyserver.mapper.UsersMapper;
import com.example.companyserver.repo.RolesRepo;
import com.example.companyserver.repo.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private RolesRepo rolesRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersMapper usersMapper;

    public UsersEntity saveUser(UsersEntity user) {
        RolesEntity userRole = rolesRepo.findByRoleName("USER");
        user.setRoles((List<RolesEntity>) userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.CREATED);
        return usersRepo.save(user);
    }

    public UsersEntity findByEmail(String email) {
        return usersRepo.findByEmail(email).orElseThrow(() -> new RuntimeException(""));
    }

    public UsersEntity findByEmailAndPassword(String email, String password) {
        UsersEntity user = findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

}