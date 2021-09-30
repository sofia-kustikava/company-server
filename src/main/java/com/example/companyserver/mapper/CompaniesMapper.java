package com.example.companyserver.mapper;

import com.example.companyserver.dto.CompaniesDto;
import com.example.companyserver.entity.CompaniesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CompaniesMapper {
    CompaniesMapper INSTANCE = Mappers.getMapper(CompaniesMapper.class);

    CompaniesDto companiesToDto(CompaniesEntity company);

    List<CompaniesDto> companiesToDto (List<CompaniesEntity> companies);

    CompaniesEntity dtoToCompanies (CompaniesDto companiesDto);
}
