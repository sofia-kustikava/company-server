package com.example.companyserver.repo;

import com.example.companyserver.entity.ReportsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsRepo extends JpaRepository<ReportsEntity, Long> {
}
