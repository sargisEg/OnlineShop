package org.shop.facade.product.impl;

import org.shop.entity.*;
import org.shop.facade.dto.*;
import org.shop.facade.mapper.core.OrderMapper;
import org.shop.facade.mapper.core.ProductCategoryMapper;
import org.shop.facade.mapper.core.ProductMapper;
import org.shop.facade.product.core.AdminFacade;
import org.shop.service.core.category.CreateProductCategoryParams;
import org.shop.service.core.category.ProductCategoryService;
import org.shop.service.core.order.OrderService;
import org.shop.service.core.product.CreateProductParams;
import org.shop.service.core.product.ProductService;
import org.shop.service.core.product.UpdateProductParams;
import org.shop.service.core.productinorder.ProductInOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

@Component
public class AdminFacadeImpl extends AbstractProductFacade implements AdminFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminFacadeImpl.class);
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final OrderService orderService;
    private final ProductInOrderService productInOrderService;
    private final ProductMapper productMapper;
    private final ProductCategoryMapper productCategoryMapper;
    private final OrderMapper orderMapper;

    public AdminFacadeImpl(ProductService productService,
                           ProductCategoryService productCategoryService,
                           OrderService orderService,
                           ProductInOrderService productInOrderService,
                           ProductMapper productMapper,
                           ProductCategoryMapper productCategoryMapper,
                           OrderMapper orderMapper
    ) {
        super(productService, productCategoryService, productMapper, productCategoryMapper);

        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.orderService = orderService;
        this.productInOrderService = productInOrderService;
        this.productMapper = productMapper;
        this.productCategoryMapper = productCategoryMapper;
        this.orderMapper = orderMapper;
    }


    @Override
    @Transactional
    public ProductCategoryDto createCategory(SaveProductCategoryRequestDto dto) {
        Assert.notNull(
                dto,
                "Class - AdminFacadeImpl, method - createCategory " +
                        "dto should not be null"
        );
        LOGGER.info("Creating product category for provided request - {}", dto);

        ProductCategory productCategory = findProductCategory(dto.getName());
        if (productCategory != null) {
            return new ProductCategoryDto(List.of("Category with name - " + dto.getName() + " already exist"));
        }

        productCategory = productCategoryService.create(new CreateProductCategoryParams(dto.getName()));

        final ProductCategoryDto productCategoryDto = productCategoryMapper.map(productCategory);

        LOGGER.info("Successfully created product category for provided request - {}, result - {}", dto, productCategoryDto);
        return productCategoryDto;
    }

    @Override
    @Transactional
    public ProductDto createProduct(SaveProductRequestDto dto) {
        Assert.notNull(
                dto,
                "Class - AdminFacadeImpl, method - createProduct " +
                        "dto should not be null"
        );
        LOGGER.info("Creating product for provided request - {}", dto);

        Product product = findProduct(dto.getName());
        if (product != null) {
            return new ProductDto(List.of("Product with name - " + dto.getName() + " already exist"));
        }

        final ProductCategory productCategory = findProductCategory(dto.getCategoryName());
        if (productCategory == null) {
            return new ProductDto(List.of("Not found category with name - " + dto.getCategoryName()));
        }

        product = productService.create(new CreateProductParams(
                dto.getName(),
                dto.getPrice(),
                productCategory.getId(),
                dto.getCount()
        ));

        final ProductDto productDto = productMapper.map(product);

        LOGGER.info("Successfully created product for provided request - {}, response - {}", dto, productDto);
        return productDto;
    }

    @Override
    @Transactional
    public ProductDto editeProduct(Long productId, SaveProductRequestDto dto) {
        Assert.notNull(
                dto,
                "Class - AdminFacadeImpl, method - editeProduct " +
                        "dto should not be null"
        );
        Assert.notNull(
                productId,
                "Class - AdminFacadeImpl, method - editeProduct " +
                        "product id should not be null"
        );
        LOGGER.info("Editing product with id - {} for provided request - {}", productId, dto);

        final Product product = findProduct(productId);
        if (product == null) {
            return new ProductDto(List.of("Not found product with id - " + productId));
        }

        final Product productFromDto = findProduct(dto.getName());
        if (productFromDto != null && !productFromDto.equals(product)) {
            return new ProductDto(List.of("Cannot change product name, product with name - " + dto.getName() + " already exist"));
        }

        final ProductCategory productCategory = findProductCategory(dto.getCategoryName());
        if (productCategory == null) {
            return new ProductDto(List.of("Not found category with name - " + dto.getCategoryName()));
        }

        final Product updatedProduct = productService.update(new UpdateProductParams(
                product.getId(),
                dto.getName(),
                dto.getPrice(),
                productCategory.getId(),
                dto.getCount()
        ));

        final ProductDto productDto = productMapper.map(updatedProduct);

        LOGGER.info("Successfully edited product with id - {} for provided request - {}, response - {}", productId, dto, productDto);
        return productDto;
    }

    @Override
    @Transactional
    public ProductDto deleteProduct(Long productId) {
        Assert.notNull(
                productId,
                "Class - AdminFacadeImpl, method - deleteProduct " +
                        "id should not be null"
        );
        LOGGER.info("Deleting product with id - {}", productId);

        final Product product = findProduct(productId);
        if (product == null) {
            return new ProductDto(List.of("Not found product with id - " + productId));
        }

        final ProductDto productDto = productMapper.map(product);

        productService.delete(product);

        LOGGER.info("Successfully deleted product with id - {}, product - {}", productId, productDto);
        return productDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrders() {
        LOGGER.info("Getting all orders");

        final List<Order> orderList = orderService.findAll();
        final List<OrderDto> orderDtoList = new LinkedList<>();
        orderList.forEach(order -> {
            final List<ProductInOrder> productInOrderList = productInOrderService.findByOrderId(order.getId());
            orderDtoList.add(orderMapper.map(order, productInOrderList));
        });

        LOGGER.info("Successfully got all orders, result - {}", orderDtoList);
        return orderDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderById(Long orderId) {
        Assert.notNull(
                orderId,
                "Class - AdminFacadeImpl, method - getOrderById " +
                        "order id should not be null"
        );
        LOGGER.info("Getting order with id - {}", orderId);

        final Order order = findOrder(orderId);
        if (order == null) {
            return new OrderDto(List.of("Not found order with id - " + orderId));
        }

        final OrderDto orderDto = orderMapper.map(order, productInOrderService.findByOrderId(orderId));

        LOGGER.info("Successfully got order with id - {}, result - {}", orderId, orderDto);
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto approveOrder(Long orderId) {
        Assert.notNull(
                orderId,
                "Class - AdminFacadeImpl, method - approveOrder " +
                        "orderId should not be null"
        );
        LOGGER.info("Approving order with orderId - {}", orderId);

        final Order order = findOrder(orderId);
        if (order == null) {
            return new OrderDto(List.of("Not found order with orderId - " + orderId));
        }

        if (order.getOrderStatus().equals(OrderStatus.WAITING)) {
            return new OrderDto(List.of("Order with id - " + orderId + " was not submitted"));
        }

        final List<ProductInOrder> productInOrderList = productInOrderService.findByOrderId(orderId);

        productInOrderList.forEach(productInOrderService::delete);

        orderService.delete(order);

        final OrderDto orderDto = orderMapper.map(order, productInOrderList);

        LOGGER.info("Successfully approved order with orderId - {}, result - {}", orderId, orderDto);
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto declineOrder(Long orderId) {
        Assert.notNull(
                orderId,
                "Class - AdminFacadeImpl, method - declineOrder " +
                        "orderId should not be null"
        );
        LOGGER.info("Submitting order with orderId - {}", orderId);

        final Order order = findOrder(orderId);
        if (order == null) {
            return new OrderDto(List.of("Not found order with orderId - " + orderId));
        }

        if (order.getOrderStatus().equals(OrderStatus.WAITING)) {
            return new OrderDto(List.of("Order with orderId - " + orderId + " was not submitted"));
        }

        final List<ProductInOrder> productInOrderList = productInOrderService.findByOrderId(orderId);

        productInOrderList.forEach(productInOrder -> {
            productInOrderService.delete(productInOrder);
            final Product product = productInOrder.getProduct();
            productService.update(new UpdateProductParams(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getCategory().getId(),
                    product.getCount() + productInOrder.getCount()
            ));
        });

        orderService.delete(order);

        final OrderDto orderDto = orderMapper.map(order, productInOrderList);

        LOGGER.info("Successfully submitted order with orderId - {}, result - {}", orderId, orderDto);
        return orderDto;
    }

    private Product findProduct(Long productId) {
        return productService.findById(productId).orElse(null);
    }

    private Product findProduct(String productName) {
        return productService.findByName(productName).orElse(null);
    }

    private ProductCategory findProductCategory(String categoryName) {
        return productCategoryService.findByName(categoryName).orElse(null);
    }

    private Order findOrder(Long orderId) {
        return orderService.findById(orderId).orElse(null);
    }
}
