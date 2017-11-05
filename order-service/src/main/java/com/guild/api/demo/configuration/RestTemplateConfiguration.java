package com.guild.api.demo.configuration;

import static com.guild.api.demo.util.rest.PooledRestTemplateBuilder.buildHystrixExecutor;
import static com.guild.api.demo.util.rest.PooledRestTemplateBuilder.buildResTemplate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
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
    public RestTemplate productServiceTemplate(RestEndpointProperties productServiceProperties) {
        return buildResTemplate(productServiceProperties);
    }

    @Bean
    public RestTemplate userServiceTemplate(RestEndpointProperties userServiceProperties) {
        return buildResTemplate(userServiceProperties);
    }

    @Bean
    public RestTemplate logisticsServiceTemplate(RestEndpointProperties logisticsServiceProperties) {
        return buildResTemplate(logisticsServiceProperties);
    }

    @Bean
    public RestTemplateExecutor productServiceTemplateExecutor(RestTemplate productServiceTemplate, RestEndpointProperties productServiceProperties) {
        return new RestTemplateExecutor(productServiceTemplate, buildHystrixExecutor(productServiceProperties), productServiceProperties);
    }

    @Bean
    public RestTemplateExecutor userServiceTemplateExecutor(RestTemplate userServiceTemplate, RestEndpointProperties userServiceProperties) {
        return new RestTemplateExecutor(userServiceTemplate, buildHystrixExecutor(userServiceProperties), userServiceProperties);
    }

    @Bean
    public RestTemplateExecutor logisticsServiceTemplateExecutor(RestTemplate logisticsServiceTemplate, RestEndpointProperties logisticsServiceProperties) {
        return new RestTemplateExecutor(logisticsServiceTemplate, buildHystrixExecutor(logisticsServiceProperties), logisticsServiceProperties);
    }
}
