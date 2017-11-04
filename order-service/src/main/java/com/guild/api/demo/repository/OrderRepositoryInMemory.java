package com.guild.api.demo.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.guild.api.demo.repository.entity.OrderEntity;

@Repository
public class OrderRepositoryInMemory implements OrderRepository {
    private Map<String, OrderEntity> orderEntityMap = new HashMap<>();

    public OrderRepositoryInMemory() {
        OrderEntity order = new OrderEntity();
        order.setOrderId("1234567890");
        order.setOrderTitle("Order Title");
        order.setDescription("This is the order description");
        order.setOrderTime("2017-10-20 10:42:55");
        order.setUserId("ID123456");
        order.setLogisticsId("LT123456");
        order.setProductId("PD123456");
        this.orderEntityMap.put(order.getOrderId(), order);
    }

    @Override
    public OrderEntity getOrder(String orderId) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.orderEntityMap.get(orderId);
    }

    @Override
    public void addOrder(OrderEntity order) {
        this.orderEntityMap.put(order.getOrderId(), order);
    }
}
