package com.example.companyserver.config;

import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.exceptions.UserNotFoundException;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;
    private final UserRepo userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepo.findByEmail(email);
        UserEntity user = userOptional.orElseThrow(() -> new UserNotFoundException(email));
        user.getRoles().forEach(role -> log.info("Reading roles"));
        return userMapper.fromUserEntityToCustomUserDetails(user);
    }
}