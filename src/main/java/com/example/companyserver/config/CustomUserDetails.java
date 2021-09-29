package com.example.companyserver.config;

import com.example.companyserver.entity.RolesEntity;
import com.example.companyserver.entity.UsersEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails fromUserEntityToCustomUserDetails(UsersEntity usersEntity) {
        CustomUserDetails c = new CustomUserDetails();
        c.email = usersEntity.getEmail();
        c.password = usersEntity.getPassword();
        mapToGrantedAuthorities(new ArrayList<>(usersEntity.getRoles()));
        return c;
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(ArrayList<RolesEntity> userRoles) {
         return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getRoleName())
                ).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
