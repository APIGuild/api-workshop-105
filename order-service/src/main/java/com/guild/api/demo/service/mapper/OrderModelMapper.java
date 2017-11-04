package com.guild.api.demo.service.mapper;

import com.guild.api.demo.mapper.BaseMapper;
import com.guild.api.demo.model.OrderModel;
import com.guild.api.demo.repository.entity.OrderEntity;

public class OrderModelMapper extends BaseMapper {
    public OrderModelMapper() {
        registerByDefault(OrderEntity.class, OrderModel.class);
    }
}
