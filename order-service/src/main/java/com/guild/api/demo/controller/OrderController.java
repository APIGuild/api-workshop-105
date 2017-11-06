package com.guild.api.demo.controller;

import static com.guild.api.demo.constant.DefaultValues.CONTENT_TYPE;
import static com.guild.api.demo.constant.DefaultValues.ORDER_ID_LENGTH_MESSAGE;
import static com.guild.api.demo.constant.DefaultValues.ORDER_ID_MAX_LENGTH;
import static com.guild.api.demo.constant.DefaultValues.ORDER_ID_MIN_LENGTH;
import static javax.servlet.http.HttpServletResponse.SC_BAD_GATEWAY;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_SERVICE_UNAVAILABLE;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.guild.api.demo.controller.dto.OrderDto;
import com.guild.api.demo.controller.dto.ResponseWrapper;
import com.guild.api.demo.controller.error.Errors;
import com.guild.api.demo.controller.translator.OrderTranslator;
import com.guild.api.demo.model.OrderModel;
import com.guild.api.demo.service.OrderService;
import com.guild.api.demo.util.logging.ApplicationLog;
import com.guild.api.demo.util.logging.PerformanceLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "Order Service", description = "Operations on Orders")
@Validated
public class OrderController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderService orderServiceSync;

    @Autowired
    private OrderTranslator orderTranslator;

    @ApplicationLog
    @PerformanceLog
    @GetMapping(value = "/orders/{orderId}", produces = CONTENT_TYPE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve Order", notes = "This is the retrieve order endpoint. More descriptions...", produces = CONTENT_TYPE)
    @ApiResponses({
            @ApiResponse(code = SC_NOT_FOUND, response = Errors.class, message = "Order not found"),
            @ApiResponse(code = SC_BAD_REQUEST, response = Errors.class, message = "Invalid path parameters."),
            @ApiResponse(code = SC_BAD_GATEWAY, response = Errors.class, message = "Error returned from downstream API."),
            @ApiResponse(code = SC_SERVICE_UNAVAILABLE, response = Errors.class, message = "Unable to communicate with downstream API."),
            @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, response = Errors.class, message = "Internal server error.")
    })
    public ResponseWrapper<OrderDto> retrieveOrder(@ApiParam(value = "Order ID", required = true)
                                                   @Size(min = ORDER_ID_MIN_LENGTH, max = ORDER_ID_MAX_LENGTH, message = ORDER_ID_LENGTH_MESSAGE)
                                                   @PathVariable String orderId) {
        OrderModel orderModel = orderService.getOrder(orderId);
//        LOGGER.info(orderModel.toString());
        return orderTranslator.translate(orderModel);
    }

    @ApplicationLog
    @PerformanceLog
    @GetMapping(value = "/orders/{orderId}/sync", produces = CONTENT_TYPE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve Order Demo", notes = "This is the retrieve order endpoint. More descriptions...", produces = CONTENT_TYPE)
    @ApiResponses({
            @ApiResponse(code = SC_NOT_FOUND, response = Errors.class, message = "Order not found"),
            @ApiResponse(code = SC_BAD_REQUEST, response = Errors.class, message = "Invalid path parameters."),
            @ApiResponse(code = SC_BAD_GATEWAY, response = Errors.class, message = "Error returned from downstream API."),
            @ApiResponse(code = SC_SERVICE_UNAVAILABLE, response = Errors.class, message = "Unable to communicate with downstream API."),
            @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, response = Errors.class, message = "Internal server error.")
    })
    public ResponseWrapper<OrderDto> retrieveOrderDemo(@ApiParam(value = "Order ID", required = true)
                                                       @Size(min = ORDER_ID_MIN_LENGTH, max = ORDER_ID_MAX_LENGTH, message = ORDER_ID_LENGTH_MESSAGE)
                                                       @PathVariable String orderId) {
        return orderTranslator.translate(orderServiceSync.getOrder(orderId));
    }
}
