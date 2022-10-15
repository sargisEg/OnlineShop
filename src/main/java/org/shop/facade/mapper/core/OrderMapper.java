package org.shop.facade.mapper.core;

import org.shop.entity.Order;
import org.shop.entity.ProductInOrder;
import org.shop.facade.dto.OrderDto;

import java.util.List;

public interface OrderMapper {

    OrderDto map(Order order, List<ProductInOrder> productInOrderList);
}
