package com.example.companyserver.repo;

import com.example.companyserver.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepo extends JpaRepository<QuoteEntity, Long> {
}
