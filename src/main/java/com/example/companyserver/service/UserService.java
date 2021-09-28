package com.example.companyserver.service;

import com.example.companyserver.entity.RolesEntity;
import com.example.companyserver.entity.Status;
import com.example.companyserver.entity.UsersEntity;
import com.example.companyserver.error.UserAlreadyExistException;
import com.example.companyserver.repo.RolesRepo;
import com.example.companyserver.repo.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UsersRepo usersRepo;
    private final RolesRepo rolesRepo;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UsersRepo usersRepo, RolesRepo rolesRepo, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
    }



    public UsersEntity findById(Long id) {
        UsersEntity result = usersRepo.findById(id).orElse(null);
        if (result == null) {
            log.warn("There is no user with this id: ", id);
            return null;
        }
        return result;
    }

    public UsersEntity findByEmail(String email) {
        UsersEntity result = usersRepo.findByEmail(email).orElse(null);
        if (result == null) {
            log.warn("There is no user with this email: ", email);
            return null;
        }
        return result;
    }

    private boolean emailExist(String email) {
        return usersRepo.findByEmail(email) != null;
    }

    public UsersEntity register(UsersEntity user) {
        if (emailExist(user.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + user.getEmail());
        }
        RolesEntity roleUser = rolesRepo.findByRoleName("USER");
        List<RolesEntity> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        UsersEntity registeredUser = usersRepo.save(user);
        log.info("Successful registration", registeredUser);

        return registeredUser;
    }

    public List<UsersEntity> findAll() {
        return usersRepo.findAll();
    }

    public void delete(Long id) {
        usersRepo.deleteById(id);
        log.info("User with this id was deleted: ", id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsersEntity user = usersRepo.findByEmail(email).orElse(null);
        if (user == null) {
            log.warn("There is no user with this email: ", email);
        }
        return new User(user.getEmail(), user.getPassword(), true, true, true, true, new ArrayList<>());
    }
}