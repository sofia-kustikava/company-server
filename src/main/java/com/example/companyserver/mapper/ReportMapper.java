package com.example.companyserver.mapper;

import com.example.companyserver.dto.ReportDto;
import com.example.companyserver.entity.ReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReportMapper {

    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    ReportDto reportToDto (ReportEntity report);
    List<ReportDto> reportsToDto (List<ReportEntity> reports);

    ReportEntity dtoToReport (ReportDto reportDto);
    List<ReportEntity> dtoToReports (List<ReportDto> reportDtos);
}
