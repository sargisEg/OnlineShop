package org.shop.facade.mapper.impl;

import org.shop.entity.ProductInOrder;
import org.shop.facade.dto.ProductInOrderDto;
import org.shop.facade.mapper.core.ProductInOrderMapper;
import org.shop.facade.mapper.core.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ProductInOrderMapperImpl implements ProductInOrderMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductMapperImpl.class);

    private final ProductMapper productMapper;

    public ProductInOrderMapperImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public ProductInOrderDto map(ProductInOrder productInOrder) {
        Assert.notNull(
                productInOrder,
                "Class - ProductInOrderMapperImpl, method - map " +
                        "product in order should not be null"
        );
        LOGGER.debug("Mapping product in order - {}", productInOrder);
        final ProductInOrderDto productInOrderDto = new ProductInOrderDto(
                productInOrder.getId(),
                productMapper.map(productInOrder.getProduct()),
                productInOrder.getCount()
        );
        LOGGER.debug("Successfully mapped product in order - {}, result - {}", productInOrder, productInOrderDto);
        return productInOrderDto;
    }
}
