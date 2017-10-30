package com.apiguild.controller;

import com.apiguild.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by bingwang on 10/24/17.
 */
@RestController
public class UserApi {
    private final static String url = "http://localhost:8083/userinfo";
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String getName() {

        ResponseEntity<UserInfo> response = restTemplate.getForEntity(url, UserInfo.class);
        String output = "my name is: " + response.getBody().userName + " my age is: " + response.getBody().age;
        logger.info(output);
        return output;
    }
}
