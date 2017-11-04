package com.guild.api.demo.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.guild.api.demo.util.rest.PooledRestTemplateBuilder;
import com.guild.api.demo.util.rest.RestEndpointProperties;
import com.guild.api.demo.util.rest.RestTemplateExecutor;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "product.service")
    public RestEndpointProperties productServiceProperties() {
        return new RestEndpointProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "user.service")
    public RestEndpointProperties userServiceProperties() {
        return new RestEndpointProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "logistics.service")
    public RestEndpointProperties logisticsServiceProperties() {
        return new RestEndpointProperties();
    }

    @Bean
    public RestTemplateExecutor productServiceTemplate(RestEndpointProperties productServiceProperties) {
        return new PooledRestTemplateBuilder(productServiceProperties).build();
    }

    @Bean
    public RestTemplateExecutor userServiceTemplate(RestEndpointProperties userServiceProperties) {
        return new PooledRestTemplateBuilder(userServiceProperties).build();
    }

    @Bean
    public RestTemplateExecutor logisticsServiceTemplate(RestEndpointProperties logisticsServiceProperties) {
        return new PooledRestTemplateBuilder(logisticsServiceProperties).build();
    }
}
