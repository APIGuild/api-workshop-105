package com.guild.api.demo.util.rest;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "product.service")
public class RestEndpointProperties {
    private static final int MINIMAL_POOL_SIZE = 10;
    private static final int MINIMAL_TIMEOUT = 1000;

    @NotBlank
    private String name;
    @NotBlank
    private String baseUrl;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Min(MINIMAL_POOL_SIZE)
    private int poolSize;
    @Min(MINIMAL_TIMEOUT)
    private int timeout;
    @Value("${reuseConnection:true}")
    private boolean reuseConnection;
}
