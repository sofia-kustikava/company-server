package com.example.companyserver.repo;

import com.example.companyserver.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyEntity, Long> {
    Optional<CompanyEntity> findBySymbol (String symbol);
    @Query("select c from CompanyEntity c where c.symbol = ?1")
    List<CompanyEntity> findAllBySymbol ();
}
