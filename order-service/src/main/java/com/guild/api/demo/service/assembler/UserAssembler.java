package com.guild.api.demo.service.assembler;

import com.guild.api.demo.controller.error.ErrorBuilder;
import com.guild.api.demo.model.UserModel;
import com.guild.api.demo.service.model.OrderContainer;
import com.guild.api.demo.util.rxjava.AsyncResult;

import rx.functions.Func2;

public class UserAssembler implements Func2<OrderContainer, AsyncResult<UserModel>, OrderContainer> {
    @Override
    public OrderContainer call(OrderContainer orderContainer, AsyncResult<UserModel> userModel) {
        if (userModel.hasException()) {
            orderContainer.addErrors(ErrorBuilder.buildServiceError(userModel.getException().getMessage()));
        } else {
            orderContainer.setUser(userModel.getValue());
        }
        return orderContainer;
    }
}
