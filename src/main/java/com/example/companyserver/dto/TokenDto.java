package com.example.companyserver.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    @NotEmpty
    private String token;
}
