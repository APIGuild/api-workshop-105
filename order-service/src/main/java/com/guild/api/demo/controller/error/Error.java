package com.guild.api.demo.controller.error;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Error")
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"status", "code", "title", "detail", "source", "links"})
public class Error {
    @ApiModelProperty(value = "Error HTTP status code", example = "400")
    private String status;

    @ApiModelProperty(value = "Error code for Service", example = "S001")
    private String code;

    @ApiModelProperty(value = "Error title", example = "Validation Error")
    private String title;

    @ApiModelProperty(value = "Error detail", example = "A field is invalid")
    private String detail;

    @ApiModelProperty(value = "The source of the error")
    private ErrorSource source;

    @ApiModelProperty(value = "A link to more information about this error")
    public ErrorLink getLink() {
        return new ErrorLink(code);
    }

}
