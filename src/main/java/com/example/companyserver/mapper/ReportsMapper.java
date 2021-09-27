package com.example.companyserver.mapper;

import com.example.companyserver.dto.ReportsDto;
import com.example.companyserver.entity.ReportsEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ReportsMapper {
    ReportsDto reportsToDto(ReportsEntity report);

    List<ReportsDto> reportsToDto (List<ReportsEntity> reports);

    ReportsEntity dtoToReport (ReportsDto reportsDto);
}
