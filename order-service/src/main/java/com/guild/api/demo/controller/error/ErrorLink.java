package com.guild.api.demo.controller.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(value = "Error Link")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorLink {
    @ApiModelProperty(value = "A link to more information about the error", example = "http://api/errors#S001", readOnly = true, required = true)
    private final String about;

    public ErrorLink(String code) {
        about = String.format("http://xxx/errors#%s", code);
    }
}
