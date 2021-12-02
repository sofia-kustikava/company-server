package com.example.companyserver.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDto {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private Double price;

    @NotNull
    @NotEmpty
    private Integer trackingSize;
}
