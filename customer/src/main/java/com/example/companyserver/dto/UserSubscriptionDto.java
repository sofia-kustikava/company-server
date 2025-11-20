package com.example.companyserver.dto;

import com.example.companyserver.entity.SubscriptionEntity;
import com.example.companyserver.entity.SubscriptionStatus;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubscriptionDto {

    private LocalDate dateStart;

    private LocalDate dateEnd;

    @NotNull
    @NotEmpty
    private SubscriptionStatus subscriptionStatus;

    @NotNull
    @NotEmpty
    private SubscriptionEntity subscription;

    @NotNull
    @NotEmpty
    private Long user;
}
