package org.shop.facade.product.core;

import org.shop.facade.dto.ProductCategoryDto;
import org.shop.facade.dto.ProductDto;

import java.util.List;

public interface ProductFacade {

    List<ProductDto> getProducts();

    List<ProductCategoryDto> getCategories();

    List<ProductDto> getProductsByCategory(Long categoryId);

    ProductDto getProductById(Long productId);

}
