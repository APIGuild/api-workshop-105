package com.guild.api.demo.controller.mapper;

import com.guild.api.demo.controller.dto.OrderDto;
import com.guild.api.demo.mapper.BaseMapper;
import com.guild.api.demo.model.OrderModel;

public class OrderDtoMapper extends BaseMapper {
    public OrderDtoMapper() {
        classMap(OrderModel.class, OrderDto.class)
                .field("orderTitle", "title")
                .field("orderTime", "time")
                .field("user.description", "user")
                .field("logistics.description", "logistics")
                .field("product.description", "product")
                .byDefault()
                .register();
    }
}
