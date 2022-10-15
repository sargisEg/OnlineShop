package org.shop.facade.mapper.impl;

import org.shop.entity.Order;
import org.shop.entity.ProductInOrder;
import org.shop.facade.dto.OrderDto;
import org.shop.facade.dto.ProductInOrderDto;
import org.shop.facade.mapper.core.OrderMapper;
import org.shop.facade.mapper.core.ProductInOrderMapper;
import org.shop.facade.mapper.core.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class OrderMapperImpl implements OrderMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductMapperImpl.class);

    private final ProductInOrderMapper productInOrderMapper;

    private final UserMapper userMapper;

    public OrderMapperImpl(ProductInOrderMapper productInOrderMapper, UserMapper userMapper) {
        this.productInOrderMapper = productInOrderMapper;
        this.userMapper = userMapper;
    }

    @Override
    public OrderDto map(Order order, List<ProductInOrder> productInOrderList) {
        LOGGER.debug("Mapping order - {}", order);

        final List<ProductInOrderDto> productInOrderDtoList = new LinkedList<>();

        productInOrderList.forEach(productInOrder -> {
            productInOrderDtoList.add(productInOrderMapper.map(productInOrder));
        });

        final OrderDto orderDto = new OrderDto(
                order.getId(),
                userMapper.map(order.getUser()),
                order.getOrderStatus(),
                productInOrderDtoList
        );

        LOGGER.debug("Successfully mapped order - {}, result - {}", order, orderDto);
        return orderDto;
    }
}
