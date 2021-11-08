package com.example.companyserver.service;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.SubscriptionStatus;
import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.exceptions.*;
import com.example.companyserver.mapper.CompanyMapper;
import com.example.companyserver.repo.CompanyRepo;
import com.example.companyserver.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingService {
    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;

    public void addUserCompany(String symbol) {
        UserEntity user = getUser();
        if (isUserHaveAccessToGetCompanies(user)) {
            CompanyEntity company = companyRepo.findBySymbol(symbol)
                    .orElseThrow(() -> new CompanyNotFoundException(String.format("%s", symbol)));
            if (!user.getSubscription().getSubscription().getName().equals("Bronze")) {
                if (user.getCompanies().size() < 3) {
                    user.getCompanies().add(company);
                    userRepo.save(user);
                } else {
                    log.info("You can't add another company.");
                    throw new MaximumCompaniesException();
                }
            } else {
                if (user.getCompanies().size() <= 2) {
                    user.getCompanies().add(company);
                    userRepo.save(user);
                } else {
                    log.info("You can't add another company.");
                    throw new MaximumCompaniesException();
                }
            }
        }
    }

    public List<CompanyDto> getUserCompanies() {
        UserEntity user = getUser();
        List<CompanyEntity> companies = user.getCompanies();
        return companyMapper.companiesToDto(companies);
    }

    private boolean isUserHaveAccessToGetCompanies(UserEntity user) {
        if (user.getSubscription() != null) {
            if (user.getSubscription().getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE)) {
                return true;
            } else if (user.getStatus().equals(UserStatus.BANNED)){
                log.info("This user is banned: {}", user.getEmail());
                throw new BannedUserException(String.format("%s", user.getEmail()));
            } else {
                log.info("Subscription is inactive for this user: {}", user.getEmail());
                throw new InactiveSubscriptionException(String.format("%s", user.getEmail()));
            }
        } else {
            log.info("There is no subscription for this user: {}", user.getEmail());
            throw new NoSubscriptionException(String.format("%s", user.getEmail()));
        }
    }

    private UserEntity getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return userRepo.findByEmail(username).orElseThrow(() -> new UserNotFoundException(username));
    }

}
