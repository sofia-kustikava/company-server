package com.example.companyserver.repo;

import com.example.companyserver.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepo extends JpaRepository<ReportEntity, Long> {
}
