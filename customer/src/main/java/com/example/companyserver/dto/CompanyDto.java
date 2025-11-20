package com.example.companyserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "symbol",
        "currency",
        "description",
        "displaySymbol",
        "figi",
        "mic",
        "type"
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDto {
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("description")
    private String description;

    @JsonProperty("displaySymbol")
    private String displaySymbol;

    @JsonProperty("figi")
    private String figi;

    @JsonProperty("mic")
    private String mic;

    @JsonProperty("type")
    private String type;


}
