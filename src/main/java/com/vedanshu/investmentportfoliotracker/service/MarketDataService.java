package com.vedanshu.investmentportfoliotracker.service;

import com.vedanshu.investmentportfoliotracker.dto.AlphaVantageQuoteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Service responsible for fetching real-time market data from external providers (Alpha Vantage).
 * Utilizes WebClient for non-blocking HTTP requests.
 *
 * @author Vedanshu Garg
 */
@Service
public class MarketDataService {

    private final WebClient webClient;

    @Value("${market-data.alpha-vantage.base-url:https://www.alphavantage.co/query}")
    private String baseUrl;

    @Value("${market-data.alpha-vantage.api-key:demo}")
    private String apiKey;

    public MarketDataService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Fetches the current live price for a given stock ticker.
     *
     * @param tickerSymbol the stock symbol (e.g., "IBM")
     * @return the current market price as a BigDecimal
     * @throws RuntimeException if the external API call fails
     */
    public BigDecimal getLivePrice(String tickerSymbol) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("www.alphavantage.co")
                        .path("/query")
                        .queryParam("function", "GLOBAL_QUOTE")
                        .queryParam("symbol", tickerSymbol)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new RuntimeException("Failed to fetch market data from provider")))
                .bodyToMono(AlphaVantageQuoteResponse.class)
                .map(response -> {
                    if (response.globalQuote() == null || response.globalQuote().price() == null) {
                        throw new RuntimeException("Invalid response or rate limit exceeded from market data provider for ticker: " + tickerSymbol);
                    }
                    return response.globalQuote().price();
                })
                // block() is used here to bridge the reactive WebClient back to our synchronous architecture
                .block();
    }
}
