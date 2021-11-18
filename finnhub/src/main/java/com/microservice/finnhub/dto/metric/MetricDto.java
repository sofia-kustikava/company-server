package com.microservice.finnhub.dto.metric;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "52WeekHigh",
        "52WeekHighDate",
        "52WeekLow",
        "52WeekLowDate",
        "52WeekPriceReturnDaily"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricDto {
    @JsonProperty("52WeekHigh")
    private Double weekHigh;

    @JsonProperty("52WeekHighDate")
    private LocalDate weekHighDate;

    @JsonProperty("52WeekLow")
    private Double weekLow;

    @JsonProperty("52WeekLowDate")
    private LocalDate weekLowDate;

    @JsonProperty("52WeekPriceReturnDaily")
    private Double weekPriceReturnDaily;
}
