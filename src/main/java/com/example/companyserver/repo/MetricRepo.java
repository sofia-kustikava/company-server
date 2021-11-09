package com.example.companyserver.repo;

import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.MetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetricRepo extends JpaRepository<MetricEntity, Long> {
    Optional<MetricEntity> findByCompanies(CompanyEntity company);
}
