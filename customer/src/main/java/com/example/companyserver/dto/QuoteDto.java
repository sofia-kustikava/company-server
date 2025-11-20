package com.example.companyserver.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

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
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDto {

    @JsonProperty("c")
    private Double currentPrice;

    @JsonProperty("d")
    private Double change;

    @JsonProperty("dp")
    private Double percentChange;

    @JsonProperty("h")
    private Double highPrice;

    @JsonProperty("l")
    private Double lowPrice;

    @JsonProperty("o")
    private Double openPrice;

    @JsonProperty("pc")
    private Double closePrice;
}
