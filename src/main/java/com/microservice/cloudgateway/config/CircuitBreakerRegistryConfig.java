package com.microservice.cloudgateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerRegistryConfig {
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> genysisCustomizer() {

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                        .slidingWindowSize(50)//Configure calls
                        .failureRateThreshold(60)
                        .waitDurationInOpenState(Duration.ofMillis(30)) //configure how long the circuit should stay in the OPEN
                        .permittedNumberOfCallsInHalfOpenState(2) //configure a number of permitted calls in the HALF_OPEN state
                        .minimumNumberOfCalls(2)
                        .slowCallDurationThreshold(Duration.ofMillis(100))//handle slow responses with threshold percentage
                        .slowCallRateThreshold(60)
                        .build())
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(1200)).build())
                .build());
    }
}
