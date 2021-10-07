package com.example.companyserver.repo;

import com.example.companyserver.entity.UserSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepo extends JpaRepository<UserSubscriptionEntity, Long> {
}
