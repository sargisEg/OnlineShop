package org.shop.facade.mapper.impl;

import org.shop.entity.ProductCategory;
import org.shop.facade.dto.ProductCategoryDto;
import org.shop.facade.mapper.core.ProductCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ProductCategoryMapperImpl implements ProductCategoryMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryMapperImpl.class);

    @Override
    public ProductCategoryDto map(ProductCategory productCategory) {
        Assert.notNull(
                productCategory,
                "Class - ProductCategoryMapperImpl, method - map " +
                        "productCategory should not be null"
        );
        LOGGER.debug("Mapping product - {}", productCategory);

        final ProductCategoryDto productCategoryDto = new ProductCategoryDto(
                productCategory.getId(),
                productCategory.getName()
        );

        LOGGER.debug("Successfully mapped product - {}, result - {}", productCategory, productCategoryDto);
        return productCategoryDto;
    }
}
