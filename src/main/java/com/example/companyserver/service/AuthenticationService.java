package com.example.companyserver.service;

import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.exceptions.UserNotFoundException;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;

    public UserEntity getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return userRepo.findByEmail(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}
