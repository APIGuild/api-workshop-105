package com.guild.api.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping(value = "/users/{userId}")
    public String getUser(@PathVariable String userId) throws InterruptedException {
        Thread.sleep(1000);
        String userInfo = String.format("{Id: %s, Name: James}", userId);
        LOGGER.info(userInfo);
        return userInfo;
    }

}
