package com.guild.api.demo.service;

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

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

// Question: Can we refactor the Observable template?

@Service(value = "orderServiceDemo")
public class OrderServiceDemoImpl implements OrderService {
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
        orderModel.setDescription("This is the order description DEMO!!!");

        Observable<AsyncResult<UserModel>> userStream = getUserAsyncStream(orderEntity.getUserId());
        Observable<AsyncResult<LogisticsModel>> logisticsStream = getLogisticsAsyncStream(orderEntity.getLogisticsId());
        Observable<AsyncResult<ProductModel>> productStream = getProductAsyncStream(orderEntity.getProductId());

        Observable<OrderContainer> orderContainer = Observable.just(new OrderContainer())
                .zipWith(userStream, new UserAssembler())
                .zipWith(logisticsStream, new LogisticsAssembler())
                .zipWith(productStream, new ProductAssembler())
                .subscribeOn(Schedulers.io());

        orderContainerMapper.map(orderContainer.blockingFirst(), orderModel);

        return orderModel;
    }

    private Observable<AsyncResult<UserModel>> getUserAsyncStream(String userId) {
        return Observable.create((ObservableOnSubscribe<AsyncResult<UserModel>>) emitter -> {
            try {
                UserModel user = userDao.getUser(userId);
                emitter.onNext(AsyncResult.success(user));
            } catch (Exception exception) {
                emitter.onNext(AsyncResult.failed(exception));
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
    }

    private Observable<AsyncResult<LogisticsModel>> getLogisticsAsyncStream(String logisticsId) {
        return Observable.create((ObservableOnSubscribe<AsyncResult<LogisticsModel>>) emitter -> {
            try {
                LogisticsModel logistics = logisticsDao.getLogistics(logisticsId);
                emitter.onNext(AsyncResult.success(logistics));
            } catch (Exception exception) {
                emitter.onNext(AsyncResult.failed(exception));
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
    }

    private Observable<AsyncResult<ProductModel>> getProductAsyncStream(String productId) {
        return Observable.create((ObservableOnSubscribe<AsyncResult<ProductModel>>) emitter -> {
            try {
                ProductModel product = productDao.getProduct(productId);
                emitter.onNext(AsyncResult.success(product));
            } catch (Exception exception) {
                emitter.onNext(AsyncResult.failed(exception));
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());

    }
}
