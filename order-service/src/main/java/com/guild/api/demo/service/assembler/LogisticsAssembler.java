package com.guild.api.demo.service.assembler;


import com.guild.api.demo.controller.error.ErrorBuilder;
import com.guild.api.demo.model.LogisticsModel;
import com.guild.api.demo.service.model.OrderContainer;
import com.guild.api.demo.util.rxjava.AsyncResult;

import rx.functions.Func2;

public class LogisticsAssembler implements Func2<OrderContainer, AsyncResult<LogisticsModel>, OrderContainer> {
    @Override
    public OrderContainer call(OrderContainer orderContainer, AsyncResult<LogisticsModel> logisticsModel) {
        if (logisticsModel.hasException()) {
            orderContainer.addErrors(ErrorBuilder.buildServiceError(logisticsModel.getException().getMessage()));
        } else {
            orderContainer.setLogistics(logisticsModel.getValue());
        }
        return orderContainer;

    }
}
