package com.guild.api.demo.service.assembler;

import com.guild.api.demo.controller.error.ErrorBuilder;
import com.guild.api.demo.model.UserModel;
import com.guild.api.demo.service.model.OrderContainer;
import com.guild.api.demo.util.rxjava.AsyncResult;

import io.reactivex.functions.BiFunction;

public class UserAssembler implements BiFunction<OrderContainer, AsyncResult<UserModel>, OrderContainer> {
    @Override
    public OrderContainer apply(OrderContainer orderContainer, AsyncResult<UserModel> userModel) throws Exception {
        if (userModel.hasException()) {
            orderContainer.addErrors(ErrorBuilder.buildServiceError(userModel.getException().getMessage()));
        } else {
            orderContainer.setUser(userModel.getValue());
        }
        return orderContainer;
    }
}
