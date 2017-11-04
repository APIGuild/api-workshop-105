package com.guild.api.demo.service.mapper;

import com.guild.api.demo.mapper.BaseMapper;
import com.guild.api.demo.model.OrderModel;
import com.guild.api.demo.service.model.OrderContainer;

public class OrderContainerMapper extends BaseMapper {
    public OrderContainerMapper() {
        registerByDefault(OrderContainer.class, OrderModel.class);
    }
}
