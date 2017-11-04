package com.guild.api.demo.controller.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(NON_EMPTY)
@JsonPropertyOrder({"meta", "id", "type", "attributes"})
public class ResourceDto<T> {

    @ApiModelProperty(value = "Meta Information")
    private final Meta meta = new Meta();

    @ApiModelProperty(value = "Resource ID", example = "C123465657656", required = true)
    private String id;

    @ApiModelProperty(value = "Resource Type", example = "RESOURCE", required = true)
    private String type = "ORDER";

    @ApiModelProperty(value = "Resource Attributes", required = true)
    private T attributes;

    @JsonInclude(NON_EMPTY)
    public static class Meta {
        @ApiModelProperty(value = "Resource Version", required = true)
        @JsonProperty("ETag")
        private Integer eTag;
    }

}
