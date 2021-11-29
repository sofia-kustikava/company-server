package com.example.companyserver.repo;

import com.example.companyserver.entity.UserEntity;
import com.example.companyserver.entity.UserSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserSubscriptionRepo extends JpaRepository<UserSubscriptionEntity, Long> {
    UserSubscriptionEntity findByUser(UserEntity user);

    @Query("select userSubEntity from UserSubscriptionEntity userSubEntity where userSubEntity.user.id = :user_id")
    UserSubscriptionEntity findByUserId(@Param("user_id") Long userId);
}
