package com.example.companyserver.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    @NotEmpty
    private Long id;

    @NotNull
    @NotEmpty
    private  String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    @Email
    private String email;

}
