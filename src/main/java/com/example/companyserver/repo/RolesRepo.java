package com.example.companyserver.repo;

import com.example.companyserver.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<RolesEntity, Long> {
    RolesEntity findByRoleName(String roleName);
}
