package com.apiguild.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bingwang on 10/24/17.
 */
@RestController
public class UserApi {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String getName() {
        logger.info("get info");
        return "my name is Jack";
    }
}
