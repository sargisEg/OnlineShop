package org.shop.facade.product.core;

import org.shop.facade.dto.*;

import java.util.List;

public interface AdminFacade extends ProductFacade{

    ProductCategoryDto createCategory(SaveProductCategoryRequestDto name);

    ProductDto createProduct(SaveProductRequestDto dto);

    ProductDto editeProduct(Long productId, SaveProductRequestDto dto);

    ProductDto deleteProduct(Long id);

    List<OrderDto> getOrders();

    OrderDto getOrderById(Long orderId);

    OrderDto approveOrder(Long orderId);

    OrderDto declineOrder(Long orderId);

}
