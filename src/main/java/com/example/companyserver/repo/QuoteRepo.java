package com.example.companyserver.repo;

import com.example.companyserver.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepo extends JpaRepository<QuoteEntity, Long> {
}
