package com.example.companyserver.mapper;

import com.example.companyserver.dto.MetricsDto;
import com.example.companyserver.entity.MetricsEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MetricsMapper {
    MetricsDto metricsToDto(MetricsEntity metric);

    List<MetricsDto> metricsToDto (List<MetricsEntity> metrics);

    MetricsEntity dtoToMetrics (MetricsDto metricsDto);
}
