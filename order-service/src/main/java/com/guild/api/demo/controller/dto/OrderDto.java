package com.guild.api.demo.controller.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Order Data")
@JsonPropertyOrder({"title", "time"})
@JsonInclude(NON_EMPTY)
public class OrderDto {
    @ApiModelProperty(value = "Order Title", example = "My Order")
    private String title;

    @ApiModelProperty(value = "Order Time", example = "2017-10-20 10:42:55")
    private String time;

    @ApiModelProperty(value = "Order Description", example = "Order Description")
    private String description;

    @ApiModelProperty(value = "User Description", example = "John Smith...")
    private String user;

    @ApiModelProperty(value = "Logistics Description", example = "sf-express...")
    private String logistics;

    @ApiModelProperty(value = "Product Description", example = "Mac Book 15...")
    private String product;
}
