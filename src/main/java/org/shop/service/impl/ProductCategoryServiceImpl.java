package org.shop.service.impl;

import org.shop.entity.ProductCategory;
import org.shop.repository.ProductCategoryRepository;
import org.shop.service.core.category.CreateProductCategoryParams;
import org.shop.service.core.category.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public ProductCategory create(CreateProductCategoryParams params) {
        Assert.notNull(
                params,
                "Class - ProductCategoryServiceImpl, method - create " +
                        "params should not be null"
        );
        LOGGER.info("Creating product category with params - {}", params);

        final ProductCategory productCategoryFromParams = new ProductCategory(params.getName());

        final ProductCategory productCategory = productCategoryRepository.save(productCategoryFromParams);

        LOGGER.info("Successfully created product category with params - {}, result - {}", params, productCategory);
        return productCategory;
    }

    @Override
    public Optional<ProductCategory> findById(Long id) {
        Assert.notNull(
                id,
                "Class - ProductCategoryServiceImpl, method - findById " +
                        "id should not be null"
        );
        LOGGER.info("Finding product category with id - {}", id);

        final Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(id);

        LOGGER.info("Successfully found product category with id - {}, result - {}", id, optionalProductCategory);
        return optionalProductCategory;
    }

    @Override
    public Optional<ProductCategory> findByName(String name) {
        Assert.hasText(
                name,
                "Class - ProductCategoryServiceImpl, method - findByName " +
                        "name should not be null or empty"
        );
        LOGGER.info("Fining product category with name - {}", name);

        final Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findByName(name);

        LOGGER.info("Successfully found product category with name - {}, result - {}", name, optionalProductCategory);
        return optionalProductCategory;
    }

    @Override
    public List<ProductCategory> findAll() {
        LOGGER.info("Finding all categories");
        final List<ProductCategory> productCategoryList = productCategoryRepository.findAll();

        LOGGER.info("Successfully found all categories result - {}", productCategoryList);
        return productCategoryList;
    }
}
