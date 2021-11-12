package com.example.companyserver.repo;

import com.example.companyserver.entity.UserSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserSubscriptionRepo extends JpaRepository<UserSubscriptionEntity, Long> {
}
