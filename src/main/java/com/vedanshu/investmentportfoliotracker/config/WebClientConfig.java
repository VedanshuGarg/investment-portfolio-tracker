package com.vedanshu.investmentportfoliotracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for external HTTP clients.
 *
 * @author Vedanshu Garg
 */
@Configuration
public class WebClientConfig {
    /**
     * Configures a generic WebClient bean for making asynchronous HTTP requests.
     *
     * @param builder the Spring Boot auto-configured WebClient.Builder
     * @return a configured WebClient instance
     */
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
