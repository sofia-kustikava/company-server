package com.example.companyserver.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    @NotNull
    @NotEmpty
    private String subscription;

    @NotNull
    @NotEmpty
    private LocalDateTime dateStart;

    @NotNull
    @NotEmpty
    private LocalDateTime dateEnd;
}
