package com.example.companyserver.repo;

import com.example.companyserver.entity.MetricsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricsRepo extends JpaRepository<MetricsEntity, Long> {
}
