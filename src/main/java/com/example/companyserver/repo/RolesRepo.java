package com.example.companyserver.repo;

import com.example.companyserver.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepo extends JpaRepository<RolesEntity, Long> {
    Optional<RolesEntity> findByRoleName(String roleName);
}
