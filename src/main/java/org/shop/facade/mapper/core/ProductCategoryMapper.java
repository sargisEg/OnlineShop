package org.shop.facade.mapper.core;

import org.shop.entity.ProductCategory;
import org.shop.facade.dto.ProductCategoryDto;

public interface ProductCategoryMapper {

    ProductCategoryDto map(ProductCategory productCategory);
}
