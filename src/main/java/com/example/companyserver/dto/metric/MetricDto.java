package com.example.companyserver.dto.metric;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "52WeekHigh",
        "52WeekHighDate",
        "52WeekLow",
        "52WeekLowDate",
        "52WeekPriceReturnDaily"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricDto {
    @JsonProperty("52WeekHigh")
    private float weekHigh;

    @JsonProperty("52WeekHighDate")
    private Date weekHighDate;

    @JsonProperty("52WeekLow")
    private float weekLow;

    @JsonProperty("52WeekLowDate")
    private Date weekLowDate;

    @JsonProperty("52WeekPriceReturnDaily")
    private float weekPriceReturnDaily;
}
