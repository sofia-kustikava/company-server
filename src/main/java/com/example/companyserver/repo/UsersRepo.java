package com.example.companyserver.repo;

import com.example.companyserver.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByEmail(String email);

    public UsersEntity getByEmail(String email);

    public List<UsersEntity> getAll();
}
