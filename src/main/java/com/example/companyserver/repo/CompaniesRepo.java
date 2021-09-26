package com.example.companyserver.repo;

import com.example.companyserver.entity.CompaniesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompaniesRepo extends JpaRepository<CompaniesEntity, Long> {
}
