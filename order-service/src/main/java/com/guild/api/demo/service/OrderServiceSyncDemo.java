package com.guild.api.demo.service;

import static com.guild.api.demo.controller.error.ErrorBuilder.buildServiceError;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guild.api.demo.dao.LogisticsDao;
import com.guild.api.demo.dao.ProductDao;
import com.guild.api.demo.dao.UserDao;
import com.guild.api.demo.dao.exception.DaoException;
import com.guild.api.demo.exception.ResourceNotFoundException;
import com.guild.api.demo.model.OrderModel;
import com.guild.api.demo.repository.OrderRepository;
import com.guild.api.demo.repository.entity.OrderEntity;
import com.guild.api.demo.service.mapper.OrderContainerMapper;
import com.guild.api.demo.service.mapper.OrderModelMapper;
import com.guild.api.demo.service.model.OrderContainer;

@Service(value = "orderServiceSync")
public class OrderServiceSyncDemo implements OrderService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LogisticsDao logisticsDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderRepository orderRepository;

    private OrderModelMapper orderModelMapper = new OrderModelMapper();
    private OrderContainerMapper orderContainerMapper = new OrderContainerMapper();

    @Override
    public OrderModel getOrder(String orderId) {
        OrderEntity orderEntity = ofNullable(orderRepository.getOrder(orderId))
                .orElseThrow(() -> new ResourceNotFoundException(format("Order ID <%s> Not Found!", orderId)));

        OrderModel orderModel = orderModelMapper.map(orderEntity, OrderModel.class);
        orderModel.setDescription("This is the zipkin demo for sync scenario!!!");

        OrderContainer orderContainer = getOrderContainerJustDemoForSyncScenario(orderEntity);

        orderContainerMapper.map(orderContainer, orderModel);

        return orderModel;
    }

    private OrderContainer getOrderContainerJustDemoForSyncScenario(OrderEntity orderEntity) {
        OrderContainer orderContainer = new OrderContainer();

        try {
            orderContainer.setUser(userDao.getUser(orderEntity.getUserId()));
        } catch (DaoException e) {
            orderContainer.addErrors(buildServiceError(e.getMessage()));
        }

        try {
            orderContainer.setProduct(productDao.getProduct(orderEntity.getProductId()));
        } catch (DaoException e) {
            orderContainer.addErrors(buildServiceError(e.getMessage()));
        }

        try {
            orderContainer.setLogistics(logisticsDao.getLogistics(orderEntity.getLogisticsId()));
        } catch (DaoException e) {
            orderContainer.addErrors(buildServiceError(e.getMessage()));
        }

        return orderContainer;
    }
}
