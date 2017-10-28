package com.apiguild.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bingwang on 10/24/17.
 */
@RestController
public class UserApi {
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String getName() {

        return "my name is Jack";
    }
}
