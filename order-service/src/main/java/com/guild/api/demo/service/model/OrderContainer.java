package com.guild.api.demo.service.model;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.synchronizedList;

import java.util.List;

import com.guild.api.demo.controller.error.Error;
import com.guild.api.demo.model.LogisticsModel;
import com.guild.api.demo.model.ProductModel;
import com.guild.api.demo.model.UserModel;

import lombok.Data;

@Data
public class OrderContainer {
    private UserModel user;
    private LogisticsModel logistics;
    private ProductModel product;
    private List<Error> errors = synchronizedList(newArrayList());

    public void addErrors(Error error) {
        errors.add(error);
    }
}
