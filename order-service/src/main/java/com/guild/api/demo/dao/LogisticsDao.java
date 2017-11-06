package com.guild.api.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import com.guild.api.demo.dao.exception.DaoException;
import com.guild.api.demo.dao.exception.DaoExceptionBuilder;
import com.guild.api.demo.model.LogisticsModel;
import com.guild.api.demo.util.logging.PerformanceLog;
import com.guild.api.demo.util.rest.RestTemplateExecutor;

@Component
public class LogisticsDao {
    private static final String RETRIEVE_LOGISTICS_URL = "{baseUrl}/logistics/{logisticsId}";
    private static final String RETRIEVE_LOGISTICS_KEY = "retrieveLogistics";

    @Autowired
    @Qualifier("logisticsServiceTemplateExecutor")
    private RestTemplateExecutor restTemplateExecutor;

    @PerformanceLog
    public LogisticsModel getLogistics(String logisticsId) throws DaoException {
        String url = UriComponentsBuilder.fromPath(RETRIEVE_LOGISTICS_URL)
                .buildAndExpand(restTemplateExecutor.getEndpointProperties().getBaseUrl(), logisticsId).toString();
        try {
            String logisticsInfo = restTemplateExecutor.getForEntity(RETRIEVE_LOGISTICS_KEY, url, String.class);
            return new LogisticsModel(logisticsId, logisticsInfo);
        } catch (Exception exception) {
            throw new DaoExceptionBuilder(url).build(exception);
        }
    }
}
