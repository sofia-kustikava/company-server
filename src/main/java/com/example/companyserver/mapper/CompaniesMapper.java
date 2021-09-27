package com.example.companyserver.mapper;

import com.example.companyserver.dto.CompaniesDto;
import com.example.companyserver.entity.CompaniesEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CompaniesMapper {
    CompaniesDto companiesToDto(CompaniesEntity company);

    List<CompaniesDto> companiesToDto (List<CompaniesEntity> companies);

    CompaniesEntity dtoToCompanies (CompaniesDto companiesDto);
}
