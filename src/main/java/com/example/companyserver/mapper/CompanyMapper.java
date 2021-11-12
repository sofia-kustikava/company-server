package com.example.companyserver.mapper;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.entity.CompanyEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CompanyMapper {

    CompanyDto companyToDto(CompanyEntity company);
    List<CompanyDto> companiesToDto (List<CompanyEntity> companies);

    CompanyEntity dtoToCompany (CompanyDto companyDto);
    List<CompanyEntity> dtoToCompanies (List<CompanyDto> companyDtos);
}
