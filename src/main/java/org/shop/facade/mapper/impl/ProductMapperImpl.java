package org.shop.facade.mapper.impl;

import org.shop.entity.Product;
import org.shop.facade.dto.ProductDto;
import org.shop.facade.mapper.core.ProductCategoryMapper;
import org.shop.facade.mapper.core.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ProductMapperImpl implements ProductMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductMapperImpl.class);

    private final ProductCategoryMapper productCategoryMapper;

    public ProductMapperImpl(ProductCategoryMapper productCategoryMapper) {
        this.productCategoryMapper = productCategoryMapper;
    }

    @Override
    public ProductDto map(Product product) {
        Assert.notNull(
                product,
                "Class - ProductMapperImpl, method - map " +
                        "product should not be null"
        );
        LOGGER.debug("Mapping product - {}", product);

        final ProductDto productDto = new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                productCategoryMapper.map(product.getCategory()),
                product.getCount()
        );

        LOGGER.debug("Successfully mapped product - {}, result - {}", product, productDto);
        return productDto;
    }
}
