package com.example.companyserver.dto.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "bs",
        "cf",
        "ic"
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitsDto {
    @JsonProperty("bs")
    private List<ReportDto> bs;

    @JsonProperty("cf")
    private List<ReportDto> cf;

    @JsonProperty("ic")
    private List<ReportDto> ic;
}
