package com.example.companyserver.service;

import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.exceptions.InvalidJwtException;
import com.example.companyserver.exceptions.UserNotFoundException;
import com.example.companyserver.repo.UserRepo;
import com.example.companyserver.security.JwtUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;

    public UserEntity getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return userRepo.findByEmail(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public Long getAuthUserId(HttpServletRequest request) {
        try {
            return jwtUtils.getUserFromToken(request);
        } catch (JwtException e) {
            throw new InvalidJwtException();
        }
    }
}
