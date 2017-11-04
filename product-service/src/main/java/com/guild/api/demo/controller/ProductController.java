package com.guild.api.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping(value = "/products/{productId}")
    public String getProduct(@PathVariable String productId) throws InterruptedException {
        Thread.sleep(1000);
        return String.format("{Id: %s, Name: Mac Book 15}", productId);
    }

}
