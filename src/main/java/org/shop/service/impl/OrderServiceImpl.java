package org.shop.service.impl;

import org.shop.entity.Order;
import org.shop.entity.User;
import org.shop.repository.OrderRepository;
import org.shop.service.core.order.CreateOrderParams;
import org.shop.service.core.order.OrderService;
import org.shop.service.core.order.UpdateOrderParams;
import org.shop.service.core.user.UserService;
import org.shop.service.impl.exceptions.OrderNotFoundException;
import org.shop.service.impl.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    public Order create(CreateOrderParams params) {
        Assert.notNull(
                params,
                "Class - OrderServiceImpl, method - create " +
                        "params should not be null"
        );
        LOGGER.info("Creating order with params - {}", params);

        final Optional<User> optionalUser = userService.findById(params.getUserId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(params.getUserId());
        }

        final Order orderFromParams = new Order(optionalUser.get(), params.getOrderStatus());

        final Order order = orderRepository.save(orderFromParams);

        LOGGER.info("Successfully created order with params - {}, result - {}", params, order);
        return order;
    }

    @Override
    @Transactional
    public Order update(UpdateOrderParams params) {
        Assert.notNull(
                params,
                "Class - OrderServiceImpl, method - update " +
                        "params should not be null"
        );
        LOGGER.info("Updating order with params - {}", params);

        final Order order = orderRepository.findById(params.getId()).orElseThrow(() -> {
            throw new OrderNotFoundException(params.getId());
        });

        final Optional<User> optionalUser = userService.findById(params.getUserId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(params.getUserId());
        }

        order.setUser(optionalUser.get());
        order.setOrderStatus(params.getOrderStatus());

        orderRepository.save(order);

        LOGGER.info("Successfully updated order with params - {}, result - {}", params, order);
        return order;
    }

    @Override
    public void delete(Order order) {
        Assert.notNull(
                order,
                "Class - OrderServiceImpl, method - delete " +
                        "order should not be null"
        );
        LOGGER.info("Deleting order - {}", order);
        orderRepository.delete(order);

        LOGGER.info("Successfully deleted order - {}", order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        Assert.notNull(
                id,
                "Class - OrderServiceImpl, method - findById " +
                        "id should not be null"
        );
        LOGGER.info("Finding order with id - {}", id);

        final Optional<Order> optionalOrder = orderRepository.findById(id);

        LOGGER.info("Successfully found order with id - {}, result - {}", id, optionalOrder);
        return optionalOrder;
    }

    @Override
    public Optional<Order> findByUserId(Long id) {
        Assert.notNull(
                id,
                "Class - OrderServiceImpl, method - findById " +
                        "id should not be null"
        );
        LOGGER.info("Finding order with user id - {}", id);

        final Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        final Optional<Order> optionalOrder = orderRepository.findByUserId(id);

        LOGGER.info("Successfully found order with user id - {}, result - {}", id, optionalOrder);
        return optionalOrder;
    }

    @Override
    public List<Order> findAll() {
        LOGGER.info("Finding all orders");

        final List<Order> orderList = orderRepository.findAll();


        LOGGER.info("Successfully found all orders, result - {}", orderList);
        return orderList;
    }
}
