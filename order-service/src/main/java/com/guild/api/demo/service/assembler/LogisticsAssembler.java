package com.guild.api.demo.service.assembler;


import com.guild.api.demo.controller.error.ErrorBuilder;
import com.guild.api.demo.model.LogisticsModel;
import com.guild.api.demo.service.model.OrderContainer;
import com.guild.api.demo.util.rxjava.AsyncResult;

import io.reactivex.functions.BiFunction;

public class LogisticsAssembler implements BiFunction<OrderContainer, AsyncResult<LogisticsModel>, OrderContainer> {
    @Override
    public OrderContainer apply(OrderContainer orderContainer, AsyncResult<LogisticsModel> logisticsModel) throws Exception {
        if (logisticsModel.hasException()) {
            orderContainer.addErrors(ErrorBuilder.buildServiceError(logisticsModel.getException().getMessage()));
        } else {
            orderContainer.setLogistics(logisticsModel.getValue());
        }
        return orderContainer;

    }
}
