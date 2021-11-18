package com.microservice.finnhub.mapper;

import com.microservice.finnhub.dto.metric.MetricDto;
import com.microservice.finnhub.entity.MetricEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MetricMapper {

    MetricDto metricToDto (MetricEntity metric);
    List<MetricDto> metricsToDto (List<MetricEntity> metrics);

    MetricEntity dtoToMetric (MetricDto metricDto);
    List<MetricEntity> dtoToMetrics (List<MetricDto> metricDtos);
}