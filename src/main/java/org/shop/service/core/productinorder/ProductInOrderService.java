package org.shop.service.core.productinorder;

import org.shop.entity.ProductInOrder;

import java.util.List;
import java.util.Optional;

public interface ProductInOrderService {

    ProductInOrder create(CreateProductInOrderParams params);

    ProductInOrder update(UpdateProductInOrderParams params);

    void delete(ProductInOrder productInOrder);

    Optional<ProductInOrder> findById(Long id);

    List<ProductInOrder> findByOrderId(Long id);

    Optional<ProductInOrder> findByOrderIdAndProductName(Long orderId, String productName);
}
