package com.guild.api.demo.model;

import lombok.Data;

@Data
public class OrderModel {
    private String orderId;
    private String orderTitle;
    private String orderTime;
    private String description;
    private UserModel user;
    private LogisticsModel logistics;
    private ProductModel product;
}
