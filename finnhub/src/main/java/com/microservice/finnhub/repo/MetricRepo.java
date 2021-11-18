package com.microservice.finnhub.repo;

import com.microservice.finnhub.entity.CompanyEntity;
import com.microservice.finnhub.entity.MetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetricRepo extends JpaRepository<MetricEntity, Long> {
    Optional<MetricEntity> findByCompanies(CompanyEntity company);
}
