package com.guild.api.demo.service;

import static com.guild.api.demo.util.rxjava.AsyncTemplate.async;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guild.api.demo.dao.LogisticsDao;
import com.guild.api.demo.dao.ProductDao;
import com.guild.api.demo.dao.UserDao;
import com.guild.api.demo.exception.ResourceNotFoundException;
import com.guild.api.demo.model.LogisticsModel;
import com.guild.api.demo.model.OrderModel;
import com.guild.api.demo.model.ProductModel;
import com.guild.api.demo.model.UserModel;
import com.guild.api.demo.repository.OrderRepository;
import com.guild.api.demo.repository.entity.OrderEntity;
import com.guild.api.demo.service.assembler.LogisticsAssembler;
import com.guild.api.demo.service.assembler.ProductAssembler;
import com.guild.api.demo.service.assembler.UserAssembler;
import com.guild.api.demo.service.mapper.OrderContainerMapper;
import com.guild.api.demo.service.mapper.OrderModelMapper;
import com.guild.api.demo.service.model.OrderContainer;
import com.guild.api.demo.util.rxjava.AsyncResult;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

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

        OrderContainer orderContainer = getOrderContainerAsync(orderEntity);

        orderContainerMapper.map(orderContainer, orderModel);

        return orderModel;
    }

    private OrderContainer getOrderContainerAsync(OrderEntity orderEntity) {
        Single<AsyncResult<UserModel>> userStream = async(() -> userDao.getUser(orderEntity.getUserId()));
        Single<AsyncResult<LogisticsModel>> logisticsStream = async(() -> logisticsDao.getLogistics(orderEntity.getLogisticsId()));
        Single<AsyncResult<ProductModel>> productStream = async(() -> productDao.getProduct(orderEntity.getProductId()));

        Single<OrderContainer> orderContainer = Single.just(new OrderContainer())
                .zipWith(userStream, new UserAssembler())
                .zipWith(logisticsStream, new LogisticsAssembler())
                .zipWith(productStream, new ProductAssembler())
                .subscribeOn(Schedulers.io());

        return orderContainer.blockingGet();
    }

}
