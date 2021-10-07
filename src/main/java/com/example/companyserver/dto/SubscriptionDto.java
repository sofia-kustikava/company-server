package com.example.companyserver.dto;

import com.example.companyserver.entity.SubscriptionStatus;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    @NotNull
    @NotEmpty
    private SubscriptionType subscription;

    @NotNull
    @NotEmpty
    private LocalDateTime dateStart;

    @NotNull
    @NotEmpty
    private Date dateEnd;

    @NotNull
    @NotEmpty
    private SubscriptionStatus subscriptionStatus;
}
