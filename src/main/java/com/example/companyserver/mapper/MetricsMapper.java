package com.example.companyserver.mapper;

import com.example.companyserver.dto.MetricsDto;
import com.example.companyserver.entity.MetricsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MetricsMapper {

    MetricsMapper INSTANCE = Mappers.getMapper(MetricsMapper.class);

    MetricsDto metricsToDto(MetricsEntity metric);

    List<MetricsDto> metricsToDto (List<MetricsEntity> metrics);

    MetricsEntity dtoToMetrics (MetricsDto metricsDto);
}
