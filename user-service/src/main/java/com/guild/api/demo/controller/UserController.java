package com.guild.api.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping(value = "/users/{userId}")
    public String getUser(@PathVariable String userId) throws InterruptedException {
        Thread.sleep(1000);
        return String.format("{Id: %s, Name: James}", userId);
    }

}
