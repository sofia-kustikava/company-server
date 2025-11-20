package com.example.companyserver.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    @NotNull
    @NotEmpty
    private String roleName;
}
