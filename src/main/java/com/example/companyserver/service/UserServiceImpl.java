package com.example.companyserver.service;

import com.example.companyserver.entity.RolesEntity;
import com.example.companyserver.entity.Status;
import com.example.companyserver.entity.UsersEntity;
import com.example.companyserver.error.UserAlreadyExistException;
import com.example.companyserver.repo.RolesRepo;
import com.example.companyserver.repo.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UsersRepo usersRepo;
    private RolesRepo rolesRepo;
    private PasswordEncoder passwordEncoder;

    @Bean
    protected PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(12);
    }

    public UserServiceImpl(UsersRepo usersRepo, RolesRepo rolesRepo) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
    }

    @Override
    public List<UsersEntity> getUsers() {
        List<UsersEntity> result = usersRepo.findAll();
        log.info("All users in the system: ", result.size());
        return result;
    }

    @Override
    public UsersEntity findById(Long id) {
        UsersEntity result = usersRepo.findById(id).orElse(null);
        if (result == null) {
            log.warn("There is no user with this id: ", id);
            return null;
        }
        return result;
    }

    @Override
    public Optional<UsersEntity> findByEmail(String email) {
        Optional<UsersEntity> result = usersRepo.findByEmail(email);
        if (result == null) {
            log.warn("There is no user with this email: ", email);
            return null;
        }
        return result;
    }

    @Override
    public UsersEntity register(UsersEntity user) throws UserAlreadyExistException {
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


    private boolean emailExist(String email) {
        return usersRepo.findByEmail(email).isPresent();
    }

    @Override
    public void saveRegisteredUser(UsersEntity user) {
        usersRepo.save(user);
    }


    @Override
    public void delete(Long id) {
        usersRepo.deleteById(id);
        log.info("User was deleted with this id: ", id);
    }
}