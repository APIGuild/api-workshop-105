package com.guild.api.demo.service.assembler;

import com.guild.api.demo.controller.error.ErrorBuilder;
import com.guild.api.demo.model.ProductModel;
import com.guild.api.demo.service.model.OrderContainer;
import com.guild.api.demo.util.rxjava.AsyncResult;

import rx.functions.Func2;

public class ProductAssembler implements Func2<OrderContainer, AsyncResult<ProductModel>, OrderContainer> {
    @Override
    public OrderContainer call(OrderContainer orderContainer, AsyncResult<ProductModel> productModel) {
        if (productModel.hasException()) {
            orderContainer.addErrors(ErrorBuilder.buildServiceError(productModel.getException().getMessage()));
        } else {
            orderContainer.setProduct(productModel.getValue());
        }
        return orderContainer;
    }
}
