package com.guild.api.demo.util.rest;

import org.springframework.web.client.RestTemplate;
import com.guild.api.demo.dao.exception.DaoException;

@FunctionalInterface
public interface RestTemplateFunc<V> {
    V execute(RestTemplate restTemplate) throws DaoException;
}
