package com.guild.api.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import com.guild.api.demo.dao.exception.DaoException;
import com.guild.api.demo.dao.exception.DaoExceptionBuilder;
import com.guild.api.demo.model.UserModel;
import com.guild.api.demo.util.logging.PerformanceLog;
import com.guild.api.demo.util.rest.RestTemplateExecutor;

@Component
public class UserDao {
    private static final String RETRIEVE_USER_URL = "{baseUrl}/users/{userId}";
    private static final String RETRIEVE_USER_KEY = "retrieveUser";

    @Autowired
    @Qualifier("userServiceTemplateExecutor")
    private RestTemplateExecutor restTemplateExecutor;

    @PerformanceLog
    public UserModel getUser(String userId) throws DaoException {
        String url = UriComponentsBuilder.fromPath(RETRIEVE_USER_URL)
                .buildAndExpand(restTemplateExecutor.getEndpointProperties().getBaseUrl(), userId).toString();
        try {
            String userInfo = restTemplateExecutor.getForEntity(RETRIEVE_USER_KEY, url, String.class);
            return new UserModel(userId, userInfo);
        } catch (Exception exception) {
            throw new DaoExceptionBuilder(url).build(exception);
        }
    }
}
