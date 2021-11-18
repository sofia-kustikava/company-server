package com.microservice.finnhub.repo;

import com.microservice.finnhub.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepo extends JpaRepository<ReportEntity, Long> {
}
