package com.example.companyserver.mapper;

import com.example.companyserver.dto.MetricDto;
import com.example.companyserver.entity.MetricEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MetricMapper {

    MetricMapper INSTANCE = Mappers.getMapper(MetricMapper.class);

    MetricDto metricToDto (MetricEntity metric);
    List<MetricDto> metricsToDto (List<MetricEntity> metrics);

    MetricEntity dtoToMetric (MetricDto metricDto);
    List<MetricEntity> dtoToMetrics (List<MetricDto> metricDtos);
}
