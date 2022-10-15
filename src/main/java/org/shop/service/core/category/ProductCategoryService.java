package org.shop.service.core.category;

import org.shop.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {

    ProductCategory create(CreateProductCategoryParams params);

    Optional<ProductCategory> findById(Long id);

    Optional<ProductCategory> findByName(String name);

    List<ProductCategory> findAll();
}
