package com.guild.api.demo.util.rest;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import com.guild.api.demo.util.hystrix.HystrixExecutor;

public class PooledRestTemplateBuilder {
    private static final int DEFAULT_CONNECTION_TIME_OUT = 2000;

    public static RestTemplate buildResTemplate(RestEndpointProperties endpointProperties) {
        ClientHttpRequestFactory requestFactory = buildRequestFactory(
                endpointProperties.isReuseConnection(),
                endpointProperties.getUsername(),
                endpointProperties.getPassword(),
                endpointProperties.getPoolSize(),
                endpointProperties.getTimeout()
        );

        return new RestTemplate(requestFactory);
    }

    public static HystrixExecutor buildHystrixExecutor(RestEndpointProperties endpointProperties) {
        return new HystrixExecutor(endpointProperties.getName())
                .withThreadPool(endpointProperties.getName(), endpointProperties.getPoolSize())
                .withTimeout(endpointProperties.getTimeout());
    }

    private static ClientHttpRequestFactory buildRequestFactory(boolean reuseConnection, String username, String password, int threadPoolSize, int readTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT)
                .setSocketTimeout(readTimeout)
                .build();

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        HttpClientBuilder clientBuilder = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(credentialsProvider)
                .setMaxConnTotal(threadPoolSize)
                .setMaxConnPerRoute(threadPoolSize)
                .setDefaultRequestConfig(requestConfig);

        if (reuseConnection) {
            clientBuilder.setConnectionReuseStrategy(NoConnectionReuseStrategy.INSTANCE);
        }

        HttpClient httpClient = clientBuilder.build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);

    }
}
