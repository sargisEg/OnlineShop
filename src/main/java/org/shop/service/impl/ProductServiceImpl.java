package org.shop.service.impl;

import org.shop.entity.Product;
import org.shop.entity.ProductCategory;
import org.shop.repository.ProductRepository;
import org.shop.service.core.category.ProductCategoryService;
import org.shop.service.core.product.CreateProductParams;
import org.shop.service.core.product.ProductService;
import org.shop.service.core.product.UpdateProductParams;
import org.shop.service.impl.exceptions.ProductCategoryNotFoundException;
import org.shop.service.impl.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductCategoryService productCategoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryService productCategoryService) {
        this.productRepository = productRepository;
        this.productCategoryService = productCategoryService;
    }

    @Override
    public Product create(CreateProductParams params) {
        Assert.notNull(
                params,
                "Class - ProductServiceImpl, method - create " +
                        "params should not be null"
        );
        LOGGER.info("Creating product with params - {}", params);

        final ProductCategory category = productCategoryService.findById(params.getCategoryId())
                .orElseThrow(() -> {
                    throw new ProductCategoryNotFoundException(params.getCategoryId());
                });

        final Product productFromParams = new Product(
                params.getName(),
                params.getPrice(),
                category,
                params.getCount()
        );

        final Product product = productRepository.save(productFromParams);

        LOGGER.info("Successfully created product with params - {}, result - {}", params, product);
        return product;
    }

    @Override
    @Transactional
    public Product update(UpdateProductParams params) {
        Assert.notNull(
                params,
                "Class - ProductServiceImpl, method - update " +
                        "params should not be null"
        );
        LOGGER.info("Updating product with params - {}", params);

        final Product product = productRepository.findById(params.getId())
                .orElseThrow(() -> {
                    throw new ProductNotFoundException(params.getId());
                });

        final ProductCategory category = productCategoryService.findById(params.getCategoryId())
                .orElseThrow(() -> {
                    throw new ProductCategoryNotFoundException(params.getCategoryId());
                });

        product.setName(params.getName());
        product.setPrice(params.getPrice());
        product.setCategory(category);
        product.setCount(params.getCount());

        productRepository.save(product);

        LOGGER.info("Successfully updated product with params - {}, result - {}", params, product);
        return product;
    }

    @Override
    public void delete(Product product) {
        Assert.notNull(
                product,
                "Class - ProductServiceImpl, method - delete " +
                        "product should not be null"
        );
        LOGGER.info("Deleting product - {}", product);
        productRepository.delete(product);

        LOGGER.info("Successfully deleted product - {}", product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        Assert.notNull(
                id,
                "Class - ProductServiceImpl, method - findById " +
                        "id should not be null"
        );
        LOGGER.info("Finding product with id - {}", id);

        final Optional<Product> optionalProduct = productRepository.findById(id);

        LOGGER.info("Successfully found product with id - {}, result - {}", id, optionalProduct);
        return optionalProduct;
    }

    @Override
    public Optional<Product> findByName(String name) {
        Assert.hasText(
                name,
                "Class - ProductServiceImpl, method - findByName " +
                        "name should not be null or empty"
        );
        LOGGER.info("Finding product with name - {}", name);

        final Optional<Product> optionalProduct = productRepository.findByName(name);

        LOGGER.info("Successfully found product with name - {}, result - {}", name, optionalProduct);
        return optionalProduct;
    }

    @Override
    public List<Product> findByCategoryId(Long id) {
        Assert.notNull(
                id,
                "Class - ProductServiceImpl, method - findByCategoryId " +
                        "id should not be null"
        );
        LOGGER.info("Finding products in category with id - {}", id);
        final List<Product> productList = productRepository.findByCategoryId(id);

        LOGGER.info("Successfully found products in category with id - {}, result - {}", id, productList);
        return productList;
    }

    @Override
    public List<Product> findAll() {
        LOGGER.info("Finding all products");

        final List<Product> productList = productRepository.findAll();

        LOGGER.info("Successfully found all products result - {}", productList);
        return productList;
    }
}
