package com.example.companyserver.utils;

import com.example.companyserver.dto.UserDto;
import com.example.companyserver.dto.UserSubscriptionDto;
import com.example.companyserver.entity.*;

import java.time.LocalDate;

public class SubscriptionData {

    public static SubscriptionEntity getSubscription() {
        return SubscriptionEntity.builder()
                .name("Golden")
                .description("Description sample")
                .price(90D)
                .build();
    }

    public static UserSubscriptionEntity getUserSubscription(LocalDate dateEnd) {
        return UserSubscriptionEntity.builder()
                .dateEnd(dateEnd)
                .dateStart(LocalDate.now())
                .subscriptionStatus(SubscriptionStatus.ACTIVE)
                .build();
    }

    public static UserSubscriptionDto getUserSubscriptionDto(LocalDate dateEnd) {
        return UserSubscriptionDto.builder()
                .dateEnd(dateEnd)
                .dateStart(LocalDate.now())
                .subscriptionStatus(SubscriptionStatus.ACTIVE)
                .build();
    }

    public static UserEntity getUser(Long id) {
        return UserEntity.builder()
                .id(id)
                .firstName("User")
                .lastName("Userovich")
                .email("user@mail.com")
                .status(UserStatus.ACTIVE)
                .build();
    }
    public static UserDto getUserDto(Long id) {
        return UserDto.builder()
                .id(id)
                .firstName("User")
                .lastName("Userovich")
                .email("user@mail.com")
                .build();
    }
}
