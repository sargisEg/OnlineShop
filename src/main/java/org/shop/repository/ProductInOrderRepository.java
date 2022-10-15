package org.shop.repository;

import org.shop.entity.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {

    List<ProductInOrder> findByOrderId(Long Id);

    Optional<ProductInOrder> findByOrderIdAndProductName(Long orderId, String productName);
}
