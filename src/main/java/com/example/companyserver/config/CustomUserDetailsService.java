package com.example.companyserver.config;

import com.example.companyserver.dto.UserDto;
import com.example.companyserver.mapper.UserMapper;
import com.example.companyserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto user = userService.findByEmail(email);
        return userMapper.fromUserDtoToCustomUserDetails(user);
    }
}