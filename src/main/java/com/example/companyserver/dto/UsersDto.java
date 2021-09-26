package com.example.companyserver.dto;

import com.example.companyserver.entity.CompaniesEntity;
import com.example.companyserver.entity.RolesEntity;
import com.example.companyserver.entity.Status;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
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
