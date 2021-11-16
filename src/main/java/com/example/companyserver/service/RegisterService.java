package com.example.companyserver.service;

import com.example.companyserver.dto.RegisterDto;
import com.example.companyserver.entity.RoleEntity;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.exceptions.InvalidUserParameterException;
import com.example.companyserver.exceptions.UserAlreadyExistException;
import com.example.companyserver.repo.RoleRepo;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public void registerUser(RegisterDto registerDto) throws InvalidUserParameterException {
        if (emailExist(registerDto.getEmail()))
            throw new UserAlreadyExistException(String.format("%s", registerDto.getEmail()));

        try {
            UserEntity user = UserEntity.builder()
                    .firstName(registerDto.getFirstName())
                    .lastName(registerDto.getLastName())
                    .email(registerDto.getEmail())
                    .password(passwordEncoder.encode(registerDto.getPassword()))
                    .status(UserStatus.CREATED)
                    .dateCreated(LocalDate.now())
                    .updated(LocalDate.now())
                    .build();

            RoleEntity userRole = roleRepo.findByRoleName("USER");
            user.setRoles(Arrays.asList(userRole));

            mailService.sendEmailRegistration(registerDto);
            userRepo.save(user);
        } catch (Exception e) {
            throw new InvalidUserParameterException(e.getMessage());
        }

    }

    public void saveUsers() throws InvalidUserParameterException {
        List<String> emails = new ArrayList<>(List.of("a1@ma.r", "21a@ma.r", "31a@ma.r"));
        for (int i = 100; i < 150; i++) {
            String email = emails.size() > 0 ? emails.remove(0):i + "@sjdklhdfhs.fr";
            RegisterDto registerDto = RegisterDto.builder()
                    .firstName(String.valueOf(i))
                    .lastName(String.valueOf(i))
                    .email(email)
                    .password(i + "00000000000")
                    .build();
            registerUser(registerDto);
        }
    }

    private boolean emailExist(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

}
