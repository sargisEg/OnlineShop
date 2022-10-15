package org.shop.controller;

import org.shop.facade.dto.*;
import org.shop.facade.product.core.AdminFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/online/shop")
public class AdminController {

    private final AdminFacade adminFacade;

    public AdminController(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }

    @PostMapping("/categories")
    public ProductCategoryDto createCategory(@RequestBody SaveProductCategoryRequestDto dto) {
        return adminFacade.createCategory(dto);
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody SaveProductRequestDto dto) {
        return adminFacade.createProduct(dto);
    }

    @PutMapping("/products/{productId}")
    public ProductDto editeProduct(@PathVariable("productId") Long productId, @RequestBody SaveProductRequestDto dto) {
        return adminFacade.editeProduct(productId, dto);
    }

    @DeleteMapping("/products/{productId}")
    public ProductDto deleteProduct(@PathVariable("productId") Long productId) {
        return adminFacade.deleteProduct(productId);
    }

    @GetMapping("/orders")
    public List<OrderDto> getOrders() {
        return adminFacade.getOrders();
    }

    @GetMapping("/orders/{orderId}")
    public OrderDto getOrderById(@PathVariable(name = "orderId") Long orderId) {
        return adminFacade.getOrderById(orderId);
    }

    @PutMapping("/orders/{orderId}")
    public OrderDto submitOrder(@PathVariable(name = "orderId") Long orderId, @RequestParam("status") Status orderStatus) {
        if (orderStatus.equals(Status.approve)) {
            return adminFacade.approveOrder(orderId);
        } else {
            return adminFacade.declineOrder(orderId);
        }
    }

    private enum Status {
        approve,
        decline
    }
}
