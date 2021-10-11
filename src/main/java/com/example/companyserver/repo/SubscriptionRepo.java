package com.example.companyserver.repo;

import com.example.companyserver.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepo extends JpaRepository<SubscriptionEntity, Long> {
    Optional<SubscriptionEntity> findById(Long id);
    Optional<SubscriptionEntity> findByName(String name);
}
