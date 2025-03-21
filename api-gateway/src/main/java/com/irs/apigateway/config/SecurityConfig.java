package com.irs.apigateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                //.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
                .authorizeExchange(exchanges -> {
                    exchanges.pathMatchers("/actuator/**").permitAll();
                    exchanges.anyExchange().authenticated();
                })
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}