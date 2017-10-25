package com.apiguild.controller;

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
public class HelloApi {
    String url = "http://localhost:8081/name";
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RestTemplate restTemplate;
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String sayHi() {
        logger.info("say hello");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String response = "hi, " + responseEntity.getBody();
        logger.info(response);
        return response;
    }
}
