package com.microservice.finnhub.mapper;

import com.microservice.finnhub.dto.CompanyDto;
import com.microservice.finnhub.entity.CompanyEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CompanyMapper {

    CompanyDto companyToDto(CompanyEntity company);
    List<CompanyDto> companiesToDto (List<CompanyEntity> companies);

    CompanyEntity dtoToCompany (CompanyDto companyDto);
    List<CompanyEntity> dtoToCompanies (List<CompanyDto> companyDtos);
}
