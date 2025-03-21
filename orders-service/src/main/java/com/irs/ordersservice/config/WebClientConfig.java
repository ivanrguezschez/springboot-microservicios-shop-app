package com.irs.ordersservice.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.DefaultClientRequestObservationConvention;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(ObservationRegistry observationRegistry) {
        // SpringWebFlux (no funciona pese a tener la dependencia en pom.xml)
        //ServerBearerExchangeFilterFunction bearer = new ServerBearerExchangeFilterFunction();

        // SpringMVC (funciona)
        ServletBearerExchangeFilterFunction bearer = new ServletBearerExchangeFilterFunction();

        return WebClient.builder()
                .filter(bearer)
                .observationRegistry(observationRegistry)
                .observationConvention(new DefaultClientRequestObservationConvention());
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
