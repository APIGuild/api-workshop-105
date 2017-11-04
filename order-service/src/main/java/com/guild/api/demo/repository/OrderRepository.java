package com.guild.api.demo.repository;

import com.guild.api.demo.repository.entity.OrderEntity;

public interface OrderRepository {
    OrderEntity getOrder(String orderId);

    void addOrder(OrderEntity order);
}
