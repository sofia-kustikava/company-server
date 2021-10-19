package com.example.companyserver.repo;

import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSubscriptionRepo extends JpaRepository<UserSubscriptionEntity, Long> {
    Optional<UserSubscriptionEntity> findByUser (UserEntity id);
}
