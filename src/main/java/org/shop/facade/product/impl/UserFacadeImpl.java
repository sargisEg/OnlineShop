package org.shop.facade.product.impl;

import org.shop.entity.*;
import org.shop.facade.dto.EditeProductsInOrderRequestDto;
import org.shop.facade.dto.OrderDto;
import org.shop.facade.mapper.core.OrderMapper;
import org.shop.facade.mapper.core.ProductCategoryMapper;
import org.shop.facade.mapper.core.ProductMapper;
import org.shop.facade.mapper.impl.ProductMapperImpl;
import org.shop.facade.product.core.UserFacade;
import org.shop.service.core.category.ProductCategoryService;
import org.shop.service.core.order.CreateOrderParams;
import org.shop.service.core.order.OrderService;
import org.shop.service.core.order.UpdateOrderParams;
import org.shop.service.core.product.ProductService;
import org.shop.service.core.product.UpdateProductParams;
import org.shop.service.core.productinorder.CreateProductInOrderParams;
import org.shop.service.core.productinorder.ProductInOrderService;
import org.shop.service.core.productinorder.UpdateProductInOrderParams;
import org.shop.service.core.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
public class UserFacadeImpl extends AbstractProductFacade implements UserFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductMapperImpl.class);

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final ProductInOrderService productInOrderService;
    private final OrderMapper orderMapper;

    public UserFacadeImpl(ProductService productService,
                          ProductCategoryService productCategoryService,
                          OrderService orderService,
                          UserService userService,
                          ProductInOrderService productInOrderService,
                          ProductMapper productMapper,
                          ProductCategoryMapper productCategoryMapper,
                          OrderMapper orderMapper
    ) {
        super(productService, productCategoryService, productMapper, productCategoryMapper);

        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
        this.productInOrderService = productInOrderService;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderByUserId(Long userId) {
        Assert.notNull(
                userId,
                "Class - UserFacadeImpl, method - getOrderByUserId " +
                        "user id should not be null"
        );
        LOGGER.info("Getting order with user id - {}", userId);

        final Order order = findOrderByUserId(userId);
        if (order == null) {
            return new OrderDto(List.of("Not found order with user id - " + userId));
        }

        final OrderDto orderDto = orderMapper.map(
                order,
                findProductsInOrderByOrderId(order.getId()));

        LOGGER.info("Successfully got order with user id - {}, result - {}", userId, orderDto);
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto addProductToOrder(Long userId, EditeProductsInOrderRequestDto dto) {
        Assert.notNull(
                dto,
                "Class - UserFacadeImpl, method - addProductToOrder " +
                        "dto should not be null"
        );
        Assert.notNull(
                userId,
                "Class - UserFacadeImpl, method - addProductToOrder " +
                        "user id should not be null"
        );
        LOGGER.info("Adding product in order with user id - {} for provided request - {}", userId, dto);

        Order order = findOrderByUserId(userId);
        if (order == null) {
            order = orderService.create(new CreateOrderParams(userId, OrderStatus.WAITING));
        }

        final Product product = findProduct(dto.getProductName());
        if (product == null) {
            return new OrderDto(List.of("Not found product with name - " + dto.getProductName()));
        }

        if (product.getCount() < dto.getProductCount()) {
            return new OrderDto(List.of("Not enough product - " + product.getName() + " requested - " + dto.getProductCount() + " actual - " + product.getCount()));
        }

        final ProductInOrder productInOrder = findProductInOrder(order.getId(), dto.getProductName());
        if (productInOrder != null) {
            productInOrderService.update(new UpdateProductInOrderParams(
                    productInOrder.getId(),
                    order.getId(),
                    product.getId(),
                    productInOrder.getCount() + dto.getProductCount()
            ));
        } else {
            productInOrderService.create(new CreateProductInOrderParams(
                    order.getId(),
                    product.getId(),
                    dto.getProductCount()
            ));
        }

        productService.update(new UpdateProductParams(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCount() - dto.getProductCount()
        ));

        final OrderDto orderDto = orderMapper.map(order, findProductsInOrderByOrderId(order.getId()));

        LOGGER.info("Successfully added product in order with user id - {} for provided request - {}, result - {}", userId, dto, orderDto);
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto removeProductFromOrder(Long userId, EditeProductsInOrderRequestDto dto) {
        Assert.notNull(
                dto,
                "Class - UserFacadeImpl, method - removeProductToOrder " +
                        "dto should not be null"
        );
        Assert.notNull(
                userId,
                "Class - UserFacadeImpl, method - removeProductToOrder " +
                        "user id should not be null"
        );
        LOGGER.info("Removing product in order with user id - {} for provided request - {}", userId, dto);

        final Order order = findOrderByUserId(userId);
        if (order == null) {
            return new OrderDto(List.of("Not found order with user id - " + userId));
        }

        final ProductInOrder productInOrder = findProductInOrder(order.getId(), dto.getProductName());
        if (productInOrder == null) {
            return new OrderDto(List.of("Not found product with name - " + dto.getProductName() + " in order with user id - " + userId));
        }
        final Product product = productInOrder.getProduct();

        long dtoProductCount = dto.getProductCount();

        if (productInOrder.getCount() <= dtoProductCount) {
            productInOrderService.delete(productInOrder);
            dtoProductCount = productInOrder.getCount();
        } else {
            productInOrderService.update(new UpdateProductInOrderParams(
                    productInOrder.getId(),
                    order.getId(),
                    product.getId(),
                    productInOrder.getCount() - dtoProductCount
            ));
        }

        productService.update(new UpdateProductParams(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCount() + dtoProductCount
        ));

        final OrderDto orderDto = orderMapper.map(order, findProductsInOrderByOrderId(order.getId()));

        LOGGER.info("Successfully removed product in order with user id - {} for provided request - {}, result - {}", userId, dto, orderDto);
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto submitOrderByUserId(Long userId) {
        Assert.notNull(
                userId,
                "Class - UserFacadeImpl, method - getOrderByUserId " +
                        "user id should not be null"
        );
        LOGGER.info("Submitting order with user id - {}", userId);

        final Order order = findOrderByUserId(userId);
        if (order == null) {
            return new OrderDto(List.of("Not found order with user id - " + userId));
        }

        final List<ProductInOrder> productInOrderList = findProductsInOrderByOrderId(order.getId());
        if (productInOrderList.isEmpty()) {
            return new OrderDto(List.of("Order with user id - " + userId + " is empty"));
        }

        final Order updatedOrder = orderService.update(new UpdateOrderParams(
                order.getId(),
                userId,
                OrderStatus.SUBMITTED
        ));

        final OrderDto orderDto = orderMapper.map(updatedOrder, productInOrderList);

        LOGGER.info("Successfully submitted order with user id - {}, result - {}", userId, orderDto);
        return orderDto;
    }

    @Override
    public User getUserByUserName(String userName) {
        Assert.hasText(
                userName,
                "Class - UserFacadeImpl, method - getUserByUserName " +
                        "user name should not be null or empty"
        );
        LOGGER.debug("Getting user from first name and last name - {}", userName);

        final String firstName = userName.split("_")[0];
        final String lastName = userName.split("_")[1];

        final Optional<User> optionalUser = userService.findByFirstNameAndLastName(firstName, lastName);

        User user = optionalUser.get();

        LOGGER.debug("Successfully got user from first name and last name - {}, id - {}", userName, user);
        return user;
    }

    private Product findProduct(String productName) {
        return productService.findByName(productName).orElse(null);
    }

    private Order findOrderByUserId(Long userId) {
        return orderService.findByUserId(userId).orElse(null);
    }

    private ProductInOrder findProductInOrder(Long orderId, String productName) {
        return productInOrderService.findByOrderIdAndProductName(orderId, productName).orElse(null);
    }

    private List<ProductInOrder> findProductsInOrderByOrderId(Long orderId) {
        return productInOrderService.findByOrderId(orderId);
    }

}
