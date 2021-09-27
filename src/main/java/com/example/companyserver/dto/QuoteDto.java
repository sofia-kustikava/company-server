package com.example.companyserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "c",
        "d",
        "dp",
        "h",
        "l",
        "o",
        "pc"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDto {

    @JsonProperty("c")
    private float currentPrice;

    @JsonProperty("d")
    private float change;

    @JsonProperty("dp")
    private float percentChange;

    @JsonProperty("h")
    private float highPrice;

    @JsonProperty("l")
    private float lowPrice;

    @JsonProperty("o")
    private float openPrice;

    @JsonProperty("pc")
    private float closePrice;
}
