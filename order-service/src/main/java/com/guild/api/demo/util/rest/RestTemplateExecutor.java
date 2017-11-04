package com.guild.api.demo.util.rest;

import org.springframework.web.client.RestTemplate;
import com.guild.api.demo.util.hystrix.HystrixExecutor;

public class RestTemplateExecutor {
    private RestTemplate restTemplate;
    private HystrixExecutor hystrixExecutor;
    private RestEndpointProperties endpointProperties;

    public RestTemplateExecutor(RestTemplate restTemplate, HystrixExecutor hystrixExecutor, RestEndpointProperties endpointProperties) {
        this.restTemplate = restTemplate;
        this.hystrixExecutor = hystrixExecutor;
        this.endpointProperties = endpointProperties;
    }

    public <T> T execute(String commandName, RestTemplateFunc<T> func) {
        return hystrixExecutor.execute(commandName, () -> func.execute(restTemplate));
    }

    public <T> T getForEntity(String commandName, String url, Class<T> responseType) {
        return hystrixExecutor.execute(commandName, () -> restTemplate.getForEntity(url, responseType)).getBody();
    }

    public RestEndpointProperties getEndpointProperties() {
        return endpointProperties;
    }
}
