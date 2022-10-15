package org.shop.facade.product.core;

import org.shop.entity.User;
import org.shop.facade.dto.EditeProductsInOrderRequestDto;
import org.shop.facade.dto.OrderDto;

public interface UserFacade extends ProductFacade{

    OrderDto getOrderByUserId(Long userId);

    OrderDto addProductToOrder(Long userId, EditeProductsInOrderRequestDto dto);

    OrderDto removeProductFromOrder(Long userId, EditeProductsInOrderRequestDto dto);

    OrderDto submitOrderByUserId(Long userId);

    User getUserByUserName(String userName);
}
