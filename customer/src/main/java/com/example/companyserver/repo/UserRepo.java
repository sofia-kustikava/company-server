package com.example.companyserver.repo;

import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(Long id);

    @Query("select user from UserEntity user where user.subscription.dateEnd = :date")
    List<UserEntity> findAllByEndDate(@Param("date") LocalDate date);

    Optional<UserEntity> findAllBySubscription(UserSubscriptionEntity subscription);
}
