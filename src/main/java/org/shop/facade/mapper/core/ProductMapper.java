package org.shop.facade.mapper.core;

import org.shop.entity.Product;
import org.shop.facade.dto.ProductDto;

public interface ProductMapper {

    ProductDto map(Product product);
}
