package com.example.companyserver.mapper;

import com.example.companyserver.dto.report.ReportDto;
import com.example.companyserver.entity.ReportEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {

    ReportDto reportToDto (ReportEntity report);
    List<ReportDto> reportsToDto (List<ReportEntity> reports);

    ReportEntity dtoToReport (ReportDto reportDto);
    List<ReportEntity> dtoToReports (List<ReportDto> reportDtos);
}
