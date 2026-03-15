package com.vedanshu.investmentportfoliotracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * Data Transfer Object representing the structure of the Alpha Vantage Global Quote API response.
 *
 * @author Vedanshu Garg
 */
public record AlphaVantageQuoteResponse(
        @JsonProperty("Global Quote") GlobalQuote globalQuote
) {
    /**
     * Nested record mapping the specific fields inside the "Global Quote" JSON object.
     */
    public record GlobalQuote(
            @JsonProperty("01. symbol") String symbol,
            @JsonProperty("05. price") BigDecimal price,
            @JsonProperty("09. change") BigDecimal change,
            @JsonProperty("10. change percent") String changePercent
    ) {}
}
