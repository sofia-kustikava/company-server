package com.example.companyserver.repo;

import com.example.companyserver.entity.SubscriptionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionsRepo extends JpaRepository<SubscriptionsEntity, Long> {
}
