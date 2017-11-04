package com.guild.api.demo.dao;

import static java.lang.String.format;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.guild.api.demo.model.UserModel;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class UserDao {
    private static final String RETRIEVE_USER_URL = "{baseUrl}/users/{userId}";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.service.baseUrl}")
    private String baseUrl;

    @HystrixCommand(groupKey = "UserService", commandKey = "retrieveUser", threadPoolKey = "retrieveUser", fallbackMethod = "reliable")
    public UserModel getUser(String userId) {
        String url = UriComponentsBuilder
                .fromPath(RETRIEVE_USER_URL)
                .buildAndExpand(baseUrl, userId)
                .toString();
        String userInfo = restTemplate.getForEntity(url, String.class).getBody();
        return new UserModel(userId, userInfo);
    }

    public UserModel reliable(String userId) {
        String info = format("User service is unavailable for now. Couldn't find the user: %s", userId);
        return new UserModel(userId, info);
    }
}
