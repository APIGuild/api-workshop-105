package com.guild.api.demo.repository.entity;

import lombok.Data;

@Data
public class OrderEntity {
    private String orderId;
    private String orderTitle;
    private String orderTime;
    private String userId;
    private String logisticsId;
    private String description;
    private String productId;

}
