package com.microservice.finnhub.mapper;

import com.microservice.finnhub.dto.report.ReportDto;
import com.microservice.finnhub.entity.ReportEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {

    ReportDto reportToDto (ReportEntity report);
    List<ReportDto> reportsToDto (List<ReportEntity> reports);

    ReportEntity dtoToReport (ReportDto reportDto);
    List<ReportEntity> dtoToReports (List<ReportDto> reportDtos);
}
