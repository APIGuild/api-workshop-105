package com.guild.api.demo.service;

import com.guild.api.demo.model.OrderModel;

public interface OrderService {
    OrderModel getOrder(String orderId);
}
