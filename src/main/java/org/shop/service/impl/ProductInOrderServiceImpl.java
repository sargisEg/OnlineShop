package org.shop.service.impl;

import org.shop.entity.Order;
import org.shop.entity.Product;
import org.shop.entity.ProductInOrder;
import org.shop.repository.ProductInOrderRepository;
import org.shop.service.core.order.OrderService;
import org.shop.service.core.product.ProductService;
import org.shop.service.core.productinorder.CreateProductInOrderParams;
import org.shop.service.core.productinorder.ProductInOrderService;
import org.shop.service.core.productinorder.UpdateProductInOrderParams;
import org.shop.service.impl.exceptions.OrderNotFoundException;
import org.shop.service.impl.exceptions.ProductInOrderNotFoundException;
import org.shop.service.impl.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ProductInOrderServiceImpl implements ProductInOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    private final ProductInOrderRepository productInOrderRepository;

    private final OrderService orderService;

    private final ProductService productService;

    public ProductInOrderServiceImpl(ProductInOrderRepository productInOrderRepository, OrderService orderService, ProductService productService) {
        this.productInOrderRepository = productInOrderRepository;
        this.orderService = orderService;
        this.productService = productService;
    }

    @Override
    public ProductInOrder create(CreateProductInOrderParams params) {
        Assert.notNull(
                params,
                "Class - ProductInOrderServiceImpl, method - create " +
                        "params should not be null"
        );
        LOGGER.info("Creating product in order with params - {}", params);

        final Order order = orderService.findById(params.getOrderId()).orElseThrow(() -> {
            throw new OrderNotFoundException(params.getOrderId());
        });

        final Product product = productService.findById(params.getProductId()).orElseThrow(() -> {
            throw new ProductNotFoundException(params.getProductId());
        });

        final ProductInOrder productInOrderFromParams = new ProductInOrder(order, product, params.getCount());

        final ProductInOrder productInOrder = productInOrderRepository.save(productInOrderFromParams);

        LOGGER.info("Successfully created product in order with params - {}, result - {}", params, productInOrder);
        return productInOrder;
    }

    @Override
    @Transactional
    public ProductInOrder update(UpdateProductInOrderParams params) {
        Assert.notNull(
                params,
                "Class - ProductInOrderServiceImpl, method - update " +
                        "params should not be null"
        );
        LOGGER.info("Updating product in order with params - {}", params);

        final ProductInOrder productInOrder = productInOrderRepository.findById(params.getId()).orElseThrow(() -> {
            throw new ProductInOrderNotFoundException(params.getId());
        });

        final Order order = orderService.findById(params.getOrderId()).orElseThrow(() -> {
            throw new OrderNotFoundException(params.getOrderId());
        });

        final Product product = productService.findById(params.getProductId()).orElseThrow(() -> {
            throw new ProductNotFoundException(params.getProductId());
        });

        productInOrder.setOrder(order);
        productInOrder.setProduct(product);
        productInOrder.setCount(params.getCount());

        productInOrderRepository.save(productInOrder);

        LOGGER.info("Successfully updated product in order with params - {}, result - {}", params, productInOrder);
        return productInOrder;
    }

    @Override
    public void delete(ProductInOrder productInOrder) {
        Assert.notNull(
                productInOrder,
                "Class - ProductInOrderServiceImpl, method - delete " +
                        "product in order should not be null"
        );
        LOGGER.info("Deleting product in order - {}", productInOrder);

        productInOrderRepository.delete(productInOrder);

        LOGGER.info("Successfully deleted product in order - {}", productInOrder);
    }

    @Override
    public Optional<ProductInOrder> findById(Long id) {
        Assert.notNull(
                id,
                "Class - ProductInOrderServiceImpl, method - finById " +
                        "id should not be null"
        );
        LOGGER.info("Finding product in order with id - {}", id);

        final Optional<ProductInOrder> optionalProductInOrder = productInOrderRepository.findById(id);

        LOGGER.info("Successfully found product in order with id - {}, result - {}", id, optionalProductInOrder);
        return optionalProductInOrder;
    }

    @Override
    public List<ProductInOrder> findByOrderId(Long id) {
        Assert.notNull(
                id,
                "Class - ProductInOrderServiceImpl, method - findByOrderId " +
                        "id should not be null"
        );
        LOGGER.info("Finding products in order with order id - {}", id);

        orderService.findById(id).orElseThrow(() -> {
            throw new OrderNotFoundException(id);
        });

        final List<ProductInOrder> productInOrderList = productInOrderRepository.findByOrderId(id);

        LOGGER.info("Successfully found products in order with order id - {}, result - {}", id, productInOrderList);
        return productInOrderList;
    }

    @Override
    public Optional<ProductInOrder> findByOrderIdAndProductName(Long orderId, String productName) {
        Assert.notNull(
                orderId,
                "Class - ProductInOrderServiceImpl, method - findByOrderIdAndProductName " +
                        "order id should not be null"
        );
        Assert.hasText(
                productName,
                "Class - ProductInOrderServiceImpl, method - findByOrderIdAndProductName " +
                        "product Name should not be null or empty"
        );
        LOGGER.info("Finding product in order with order id - {}, and product name - {}", orderId, productName);

        final Optional<ProductInOrder> optionalProductInOrder = productInOrderRepository.findByOrderIdAndProductName(orderId, productName);

        LOGGER.info("Successfully found product in order with order id - {}, and product name - {}, result - {}", orderId, productName, optionalProductInOrder);
        return optionalProductInOrder;
    }
}
