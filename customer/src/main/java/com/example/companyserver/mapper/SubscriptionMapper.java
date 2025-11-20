package com.example.companyserver.mapper;

import com.example.companyserver.dto.SubscriptionDto;
import com.example.companyserver.entity.SubscriptionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SubscriptionMapper {

    SubscriptionDto subscriptionToDto (SubscriptionEntity subscription);
    List<SubscriptionDto> subscriptionsToDto (List<SubscriptionEntity> subscriptions);

    SubscriptionEntity dtoToSubscription (SubscriptionDto subscriptionDto);
    List<SubscriptionEntity> DtoToSubscriptions (List<SubscriptionDto> subscriptionDtos);
}
