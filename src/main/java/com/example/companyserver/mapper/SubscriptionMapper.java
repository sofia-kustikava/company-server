package com.example.companyserver.mapper;

import com.example.companyserver.dto.SubscriptionDto;
import com.example.companyserver.entity.UserSubscriptionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface SubscriptionMapper {

    SubscriptionDto subscriptionToDto (UserSubscriptionEntity subscription);

    UserSubscriptionEntity dtoToSubscription (SubscriptionDto subscriptionDto);
}
