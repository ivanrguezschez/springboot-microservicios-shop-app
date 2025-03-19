package com.irs.ordersservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        // SpringWebFlux (no funciona pese a tener la dependencia en pom.xml)
        //ServerBearerExchangeFilterFunction bearer = new ServerBearerExchangeFilterFunction();

        // SpringMVC (funciona)
        ServletBearerExchangeFilterFunction bearer = new ServletBearerExchangeFilterFunction();

        return WebClient.builder().filter(bearer);
    }
}
