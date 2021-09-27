package com.example.companyserver.repo;

import com.example.companyserver.entity.CompaniesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompaniesRepo extends JpaRepository<CompaniesEntity, Long> {
    Optional<CompaniesEntity> findBySymbol (String symbol);
}
