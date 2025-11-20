package com.example.companyserver.repo;

import com.example.companyserver.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyEntity, Long> {
    Optional<CompanyEntity> findBySymbol (String symbol);
}
