package com.microservice.finnhub.repo;

import com.microservice.finnhub.entity.CompanyEntity;
import com.microservice.finnhub.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepo extends JpaRepository<QuoteEntity, Long> {
    List<QuoteEntity> findByCompanies(CompanyEntity company);
}
