package com.example.companyserver.mapper;

import com.example.companyserver.dto.SubscriptionDto;
import com.example.companyserver.entity.SubscriptionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface SubscriptionMapper {

    SubscriptionDto subscriptionToDto (SubscriptionEntity subscription);

    SubscriptionEntity dtoToSubscription (SubscriptionDto subscriptionDto);
}
