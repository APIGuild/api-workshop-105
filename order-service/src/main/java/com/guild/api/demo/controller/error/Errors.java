package com.guild.api.demo.controller.error;

import static java.util.Arrays.asList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel(value = "Errors")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Errors {
    private final List<Error> errors;

    public Errors(Error... errors) {
        this(asList(errors));
    }
}
