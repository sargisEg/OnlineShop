package org.shop.service.core.product;

import org.shop.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product create(CreateProductParams params);

    Product update(UpdateProductParams params);

    void delete(Product product);

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    List<Product> findByCategoryId(Long id);

    List<Product> findAll();
}
