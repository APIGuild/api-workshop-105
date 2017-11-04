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

    public <T> T execute(String serviceName, RestTemplateFunc<T> func) {
        return hystrixExecutor.execute(serviceName, () -> func.execute(restTemplate));
    }

    public RestEndpointProperties getEndpointProperties() {
        return endpointProperties;
    }
}
