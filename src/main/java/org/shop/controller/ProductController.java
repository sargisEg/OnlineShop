package org.shop.controller;

import org.shop.facade.dto.ProductCategoryDto;
import org.shop.facade.dto.ProductDto;
import org.shop.facade.product.core.AdminFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/online/shop")
public class ProductController {

    private final AdminFacade adminFacade;

    public ProductController(AdminFacade adminFacade) {
        this.adminFacade = adminFacade;
    }


    @GetMapping("/products")
    public List<ProductDto> getProducts() {
        return adminFacade.getProducts();
    }

    @GetMapping("/products/{productId}")
    public ProductDto getProductById(@PathVariable("productId") Long productId) {
        return adminFacade.getProductById(productId);
    }

    @GetMapping("/categories")
    public List<ProductCategoryDto> getCategories() {
        return adminFacade.getCategories();
    }

    @GetMapping("/categories/{categoryId}")
    public List<ProductDto> getProductsByCategory(@PathVariable("categoryId") Long categoryId) {
        return adminFacade.getProductsByCategory(categoryId);
    }

}
