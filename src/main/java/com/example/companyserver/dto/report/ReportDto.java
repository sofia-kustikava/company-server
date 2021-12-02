package com.example.companyserver.dto.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "unit",
        "label",
        "value",
        "concept"
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDto {

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("label")
    private String label;

    @JsonProperty("value")
    private String value;

    @JsonProperty("concept")
    private String concept;
}
