package com.example.companyserver.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionNameDto {
    @NotNull
    @NotEmpty
    private String name;
}
