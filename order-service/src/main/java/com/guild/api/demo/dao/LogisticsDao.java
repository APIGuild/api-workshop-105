package com.guild.api.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.guild.api.demo.model.LogisticsModel;

@Component
public class LogisticsDao {
    private static final String RETRIEVE_LOGISTICS_URL = "{baseUrl}/logistics/{logisticsId}";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${logistics.service.baseUrl}")
    private String baseUrl;

    public LogisticsModel getLogistics(String logisticsId) {
        String url = UriComponentsBuilder
                .fromPath(RETRIEVE_LOGISTICS_URL)
                .buildAndExpand(baseUrl, logisticsId)
                .toString();
        String logisticsInfo = restTemplate.getForEntity(url, String.class).getBody();
        return new LogisticsModel(logisticsId, logisticsInfo);
    }
}
