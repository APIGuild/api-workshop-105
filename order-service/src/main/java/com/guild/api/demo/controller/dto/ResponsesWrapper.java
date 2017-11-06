package com.guild.api.demo.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponsesWrapper<T> {
    private List<ResourceDto<T>> data;
}
