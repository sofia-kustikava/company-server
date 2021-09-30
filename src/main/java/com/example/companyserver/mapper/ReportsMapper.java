package com.example.companyserver.mapper;

import com.example.companyserver.dto.ReportsDto;
import com.example.companyserver.entity.ReportsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReportsMapper {

    ReportsMapper INSTANCE = Mappers.getMapper(ReportsMapper.class);

    ReportsDto reportsToDto(ReportsEntity report);

    List<ReportsDto> reportsToDto (List<ReportsEntity> reports);

    ReportsEntity dtoToReport (ReportsDto reportsDto);
}
