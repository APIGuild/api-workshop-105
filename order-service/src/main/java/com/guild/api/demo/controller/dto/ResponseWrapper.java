package com.guild.api.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseWrapper<T> {
    private ResourceDto<T> data;
}
