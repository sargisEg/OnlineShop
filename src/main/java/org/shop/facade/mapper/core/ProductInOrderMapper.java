package org.shop.facade.mapper.core;

import org.shop.entity.ProductInOrder;
import org.shop.facade.dto.ProductInOrderDto;

public interface ProductInOrderMapper {

    ProductInOrderDto map(ProductInOrder productInOrder);
}
