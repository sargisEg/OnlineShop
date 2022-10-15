package org.shop.service.core.order;

import org.shop.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order create(CreateOrderParams params);

    Order update(UpdateOrderParams params);

    void delete(Order order);

    Optional<Order> findById(Long id);

    Optional<Order> findByUserId(Long id);

    List<Order> findAll();

}
