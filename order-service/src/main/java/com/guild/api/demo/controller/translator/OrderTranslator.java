package com.guild.api.demo.controller.translator;

import org.springframework.stereotype.Component;
import com.guild.api.demo.controller.dto.OrderDto;
import com.guild.api.demo.controller.dto.ResourceDto;
import com.guild.api.demo.controller.dto.ResponseWrapper;
import com.guild.api.demo.controller.mapper.OrderDtoMapper;
import com.guild.api.demo.model.OrderModel;

@Component
public class OrderTranslator {
    private OrderDtoMapper mapper = new OrderDtoMapper();

    public ResponseWrapper<OrderDto> translate(OrderModel order) {
        ResourceDto<OrderDto> resourceData = new ResourceDto<>();
        if (order != null) {
            resourceData.setAttributes(mapper.map(order, OrderDto.class));
            resourceData.setId(order.getOrderId());
        }
        return new ResponseWrapper<>(resourceData);
    }
}
