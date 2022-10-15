package org.shop.facade.product.impl;

import org.shop.entity.Product;
import org.shop.entity.ProductCategory;
import org.shop.facade.dto.ProductCategoryDto;
import org.shop.facade.dto.ProductDto;
import org.shop.facade.mapper.core.ProductCategoryMapper;
import org.shop.facade.mapper.core.ProductMapper;
import org.shop.facade.mapper.impl.ProductMapperImpl;
import org.shop.facade.product.core.ProductFacade;
import org.shop.service.core.category.ProductCategoryService;
import org.shop.service.core.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

@Component
abstract public class AbstractProductFacade implements ProductFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductMapperImpl.class);

    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final ProductMapper productMapper;
    private final ProductCategoryMapper productCategoryMapper;

    public AbstractProductFacade(ProductService productService,
                                 ProductCategoryService productCategoryService,
                                 ProductMapper productMapper,
                                 ProductCategoryMapper productCategoryMapper
    ) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.productMapper = productMapper;
        this.productCategoryMapper = productCategoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProducts() {
        LOGGER.info("Getting all products");

        final List<Product> productList = productService.findAll();
        final List<ProductDto> productDtoList = new LinkedList<>();
        productList.forEach(product -> {
            productDtoList.add(productMapper.map(product));
        });

        LOGGER.info("Successfully got all products");
        return productDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductCategoryDto> getCategories() {
        LOGGER.info("Getting all categories");

        final List<ProductCategory> productCategoryList = productCategoryService.findAll();
        final List<ProductCategoryDto> productCategoryDtoList = new LinkedList<>();
        productCategoryList.forEach(productCategory -> {
            productCategoryDtoList.add(productCategoryMapper.map(productCategory));
        });

        LOGGER.info("Successfully got all categories");
        return productCategoryDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        Assert.notNull(
                categoryId,
                "Class - AdminFacadeImpl, method - getProductsByCategory " +
                        "category id should not be null"
        );
        LOGGER.info("Getting all products from category with id - {}", categoryId);

        final ProductCategory productCategory = findProductCategory(categoryId);
        if (productCategory == null) {
            return List.of(new ProductDto(List.of("Not found category with id - " + categoryId)));
        }

        final List<Product> productList = productService.findByCategoryId(categoryId);
        final List<ProductDto> productDtoList = new LinkedList<>();

        productList.forEach(product -> {
            productDtoList.add(productMapper.map(product));
        });

        LOGGER.info("Successfully got all products from category with id - {}, result - {}", categoryId, productDtoList);
        return productDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long productId) {
        Assert.notNull(
                productId,
                "Class - AdminFacadeImpl, method - getProductById " +
                        "product id should not be null"
        );
        LOGGER.info("Getting product with id - {}", productId);


        final Product product = findProduct(productId);
        if (product == null) {
            return new ProductDto(List.of("Not found product with id - " + productId));
        }

        final ProductDto productDto = productMapper.map(product);

        LOGGER.info("Successfully got product with id - {}, result - {}", productId, productDto);
        return productDto;
    }

    private Product findProduct(Long productId) {
        return productService.findById(productId).orElse(null);
    }

    private ProductCategory findProductCategory(Long categoryId) {
        return productCategoryService.findById(categoryId).orElse(null);
    }

}
