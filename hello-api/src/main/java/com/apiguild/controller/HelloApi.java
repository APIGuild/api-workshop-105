package com.apiguild.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bingwang on 10/24/17.
 */
@RestController
public class HelloApi {
    String url = "http://localhost:8081/name";

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String sayHi() {
        return "hello";
    }
}
