package org.shop.facade.product.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.shop.entity.*;
import org.shop.facade.dto.EditeProductsInOrderRequestDto;
import org.shop.facade.dto.OrderDto;
import org.shop.facade.mapper.core.OrderMapper;
import org.shop.facade.mapper.core.ProductCategoryMapper;
import org.shop.facade.mapper.core.ProductMapper;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFacadeImplTest {

    private UserFacade testSubject;

    @Mock
    private ProductService productService;
    @Mock
    private ProductCategoryService productCategoryService;
    @Mock
    private OrderService orderService;
    @Mock
    private UserService userService;
    @Mock
    private ProductInOrderService productInOrderService;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductCategoryMapper productCategoryMapper;
    @Mock
    private OrderMapper orderMapper;

    @BeforeEach
    public void init() {
        testSubject = new UserFacadeImpl(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetOrderByUserIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.getOrderByUserId(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testAddProductToOrderWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.addProductToOrder(null, new EditeProductsInOrderRequestDto());
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testAddProductToOrderWhenDtoIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.addProductToOrder(10L, null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testRemoveProductFromOrderWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.removeProductFromOrder(null, new EditeProductsInOrderRequestDto());
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testRemoveProductFromOrderWhenDtoIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.removeProductFromOrder(10L, null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testSubmitOrderByUserIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.submitOrderByUserId(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetUserByUserNameWhenNameIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.getUserByUserName(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetUserByUserNameWhenNameIsEmpty() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.getUserByUserName("");
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetOrderByUserIdWhenOrderNotFound() {
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.getOrderByUserId(1L))
                .isEqualTo(new OrderDto(List.of("Not found order with user id - 1")));

        verify(orderService).findByUserId(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetOrderByUserId() {
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.of(new Order()));
        when(productInOrderService.findByOrderId(null))
                .thenReturn(List.of(new ProductInOrder()));
        when(orderMapper.map(new Order(), List.of(new ProductInOrder())))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.getOrderByUserId(1L))
                .isEqualTo(new OrderDto(List.of("")));

        verify(orderService).findByUserId(1L);
        verify(productInOrderService).findByOrderId(null);
        verify(orderMapper).map(new Order(), List.of(new ProductInOrder()));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testAddProductInOrderWhenOrderExistAndProductNotFound() {
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.of(new Order()));
        when(productService.findByName("name"))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.addProductToOrder(1L, new EditeProductsInOrderRequestDto("name", 10L)))
                .isEqualTo(new OrderDto(List.of("Not found product with name - name")));

        verify(orderService).findByUserId(1L);
        verify(productService).findByName("name");

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testAddProductInOrderWhenOrderExistAndNotEnoughProduct() {
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.of(new Order()));

        final Product product = new Product();
        product.setCount(5L);
        product.setName("name");
        when(productService.findByName("name"))
                .thenReturn(Optional.of(product));

        Assertions.assertThat(testSubject.addProductToOrder(1L, new EditeProductsInOrderRequestDto("name", 10L)))
                .isEqualTo(new OrderDto(
                        List.of("Not enough product - name requested - 10 actual - 5")
                ));

        verify(orderService).findByUserId(1L);
        verify(productService).findByName("name");

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testAddProductInOrderWhenOrderAndProductInOrderExist() {
        final Order order = new Order();
        order.setId(1L);
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.of(order));

        final Product product = new Product();
        product.setId(1L);
        product.setPrice(100L);
        product.setCount(15L);
        product.setName("name");
        final ProductCategory category = new ProductCategory();
        category.setId(1L);
        product.setCategory(category);
        when(productService.findByName("name"))
                .thenReturn(Optional.of(product));

        final ProductInOrder productInOrder = new ProductInOrder();
        productInOrder.setId(1L);
        productInOrder.setCount(5L);
        when(productInOrderService.findByOrderIdAndProductName(1L, "name"))
                .thenReturn(Optional.of(productInOrder));

        when(productInOrderService.findByOrderId(1L))
                .thenReturn(List.of(new ProductInOrder()));

        when(orderMapper.map(order, List.of(new ProductInOrder())))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.addProductToOrder(1L, new EditeProductsInOrderRequestDto("name", 10L)))
                .isEqualTo(new OrderDto(
                        List.of("")
                ));

        verify(orderService).findByUserId(1L);
        verify(productService).findByName("name");
        verify(productInOrderService).findByOrderIdAndProductName(1L, "name");
        verify(productInOrderService).update(
                new UpdateProductInOrderParams(
                        1L,
                        1L,
                        1L,
                        15L
                )
        );
        verify(productService).update(
                new UpdateProductParams(
                        1L,
                        "name",
                        100L,
                        1L,
                        5L
                )
        );
        verify(orderMapper).map(order, List.of(new ProductInOrder()));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testAddProductInOrderWhenOrderExistAndProductInOrderNotExist() {
        final Order order = new Order();
        order.setId(1L);
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.of(order));

        final Product product = new Product();
        product.setId(1L);
        product.setPrice(100L);
        product.setCount(15L);
        product.setName("name");
        final ProductCategory category = new ProductCategory();
        category.setId(1L);
        product.setCategory(category);
        when(productService.findByName("name"))
                .thenReturn(Optional.of(product));

        when(productInOrderService.findByOrderIdAndProductName(1L, "name"))
                .thenReturn(Optional.empty());

        when(productInOrderService.findByOrderId(1L))
                .thenReturn(List.of(new ProductInOrder()));

        when(orderMapper.map(order, List.of(new ProductInOrder())))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.addProductToOrder(1L, new EditeProductsInOrderRequestDto("name", 10L)))
                .isEqualTo(new OrderDto(
                        List.of("")
                ));

        verify(orderService).findByUserId(1L);
        verify(productService).findByName("name");
        verify(productInOrderService).findByOrderIdAndProductName(1L, "name");
        verify(productInOrderService).create(
                new CreateProductInOrderParams(
                        1L,
                        1L,
                        10L
                )
        );
        verify(productService).update(
                new UpdateProductParams(
                        1L,
                        "name",
                        100L,
                        1L,
                        5L
                )
        );
        verify(orderMapper).map(order, List.of(new ProductInOrder()));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testAddProductInOrderWhenOrderNotExistAndProductNotFound() {
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.empty());
        when(orderService.create(
                new CreateOrderParams(1L, OrderStatus.WAITING)
        )).thenReturn(new Order());

        when(productService.findByName("name"))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.addProductToOrder(1L, new EditeProductsInOrderRequestDto("name", 10L)))
                .isEqualTo(new OrderDto(List.of("Not found product with name - name")));

        verify(orderService).findByUserId(1L);
        verify(orderService).create(new CreateOrderParams(1L, OrderStatus.WAITING));
        verify(productService).findByName("name");

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testAddProductInOrderWhenOrderNotExistAndNotEnoughProduct() {
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.empty());
        when(orderService.create(
                new CreateOrderParams(1L, OrderStatus.WAITING)
        )).thenReturn(new Order());

        final Product product = new Product();
        product.setCount(5L);
        product.setName("name");
        when(productService.findByName("name"))
                .thenReturn(Optional.of(product));

        Assertions.assertThat(testSubject.addProductToOrder(1L, new EditeProductsInOrderRequestDto("name", 10L)))
                .isEqualTo(new OrderDto(
                        List.of("Not enough product - name requested - 10 actual - 5")
                ));

        verify(orderService).findByUserId(1L);
        verify(orderService).create(new CreateOrderParams(1L, OrderStatus.WAITING));
        verify(productService).findByName("name");

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testAddProductInOrderWhenOrderNotExistAndProductInOrderExist() {
        final Order order = new Order();
        order.setId(1L);
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.empty());
        when(orderService.create(
                new CreateOrderParams(1L, OrderStatus.WAITING)
        )).thenReturn(order);

        final Product product = new Product();
        product.setId(1L);
        product.setPrice(100L);
        product.setCount(15L);
        product.setName("name");
        final ProductCategory category = new ProductCategory();
        category.setId(1L);
        product.setCategory(category);
        when(productService.findByName("name"))
                .thenReturn(Optional.of(product));

        final ProductInOrder productInOrder = new ProductInOrder();
        productInOrder.setId(1L);
        productInOrder.setCount(5L);
        when(productInOrderService.findByOrderIdAndProductName(1L, "name"))
                .thenReturn(Optional.of(productInOrder));

        when(productInOrderService.findByOrderId(1L))
                .thenReturn(List.of(new ProductInOrder()));

        when(orderMapper.map(order, List.of(new ProductInOrder())))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.addProductToOrder(1L, new EditeProductsInOrderRequestDto("name", 10L)))
                .isEqualTo(new OrderDto(
                        List.of("")
                ));

        verify(orderService).findByUserId(1L);
        verify(orderService).create(new CreateOrderParams(1L, OrderStatus.WAITING));
        verify(productService).findByName("name");
        verify(productInOrderService).findByOrderIdAndProductName(1L, "name");
        verify(productInOrderService).update(
                new UpdateProductInOrderParams(
                        1L,
                        1L,
                        1L,
                        15L
                )
        );
        verify(productService).update(
                new UpdateProductParams(
                        1L,
                        "name",
                        100L,
                        1L,
                        5L
                )
        );
        verify(orderMapper).map(order, List.of(new ProductInOrder()));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testAddProductInOrderWhenOrderAndProductInOrderNotExist() {
        final Order order = new Order();
        order.setId(1L);
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.empty());
        when(orderService.create(
                new CreateOrderParams(1L, OrderStatus.WAITING)
        )).thenReturn(order);

        final Product product = new Product();
        product.setId(1L);
        product.setPrice(100L);
        product.setCount(15L);
        product.setName("name");
        final ProductCategory category = new ProductCategory();
        category.setId(1L);
        product.setCategory(category);
        when(productService.findByName("name"))
                .thenReturn(Optional.of(product));

        when(productInOrderService.findByOrderIdAndProductName(1L, "name"))
                .thenReturn(Optional.empty());

        when(productInOrderService.findByOrderId(1L))
                .thenReturn(List.of(new ProductInOrder()));

        when(orderMapper.map(order, List.of(new ProductInOrder())))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.addProductToOrder(1L, new EditeProductsInOrderRequestDto("name", 10L)))
                .isEqualTo(new OrderDto(
                        List.of("")
                ));

        verify(orderService).findByUserId(1L);
        verify(orderService).create(new CreateOrderParams(1L, OrderStatus.WAITING));
        verify(productService).findByName("name");
        verify(productInOrderService).findByOrderIdAndProductName(1L, "name");
        verify(productInOrderService).create(
                new CreateProductInOrderParams(
                        1L,
                        1L,
                        10L
                )
        );
        verify(productService).update(
                new UpdateProductParams(
                        1L,
                        "name",
                        100L,
                        1L,
                        5L
                )
        );
        verify(orderMapper).map(order, List.of(new ProductInOrder()));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testRemoveProductFromOrderWhenOrderNotFound() {
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.removeProductFromOrder(1L, new EditeProductsInOrderRequestDto()))
                .isEqualTo(new OrderDto(List.of("Not found order with user id - 1")));

        verify(orderService).findByUserId(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testRemoveProductFromOrderWhenProductInOrderNotFound() {
        final Order order = new Order();
        order.setId(1L);
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.of(order));
        when(productInOrderService.findByOrderIdAndProductName(1L, "name"))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.removeProductFromOrder(1L, new EditeProductsInOrderRequestDto("name", 10L)))
                .isEqualTo(new OrderDto(List.of("Not found product with name - name in order with user id - 1")));

        verify(orderService).findByUserId(1L);
        verify(productInOrderService).findByOrderIdAndProductName(1L, "name");

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testRemoveProductFromOrderWhenProductInOrderCountGreaterThanDtoProductCount() {
        final Order order = new Order();
        order.setId(1L);
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.of(order));

        final ProductCategory category = new ProductCategory();
        category.setId(4L);
        final Product product = new Product("pName", 100L, category, 50L);
        product.setId(3L);
        final ProductInOrder productInOrder = new ProductInOrder(order, product, 20L);
        productInOrder.setId(2L);
        when(productInOrderService.findByOrderIdAndProductName(1L, "name"))
                .thenReturn(Optional.of(productInOrder));

        when(productInOrderService.findByOrderId(1L))
                .thenReturn(List.of(new ProductInOrder()));

        when(orderMapper.map(order, List.of(new ProductInOrder())))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.removeProductFromOrder(1L, new EditeProductsInOrderRequestDto("name", 10L)))
                .isEqualTo(new OrderDto(List.of("")));

        verify(orderService).findByUserId(1L);
        verify(productInOrderService).findByOrderIdAndProductName(1L, "name");
        verify(productInOrderService).update(new UpdateProductInOrderParams(
                2L,
                1L,
                3L,
                10L
        ));
        verify(productService).update(new UpdateProductParams(
                3L,
                "pName",
                100L,
                4L,
                60L
        ));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testRemoveProductFromOrderWhenProductInOrderCountSmallerThanDtoProductCount() {
        final Order order = new Order();
        order.setId(1L);
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.of(order));

        final ProductCategory category = new ProductCategory();
        category.setId(4L);
        final Product product = new Product("pName", 100L, category, 50L);
        product.setId(3L);
        final ProductInOrder productInOrder = new ProductInOrder(order, product, 20L);
        productInOrder.setId(2L);
        when(productInOrderService.findByOrderIdAndProductName(1L, "name"))
                .thenReturn(Optional.of(productInOrder));

        when(productInOrderService.findByOrderId(1L))
                .thenReturn(List.of(new ProductInOrder()));

        when(orderMapper.map(order, List.of(new ProductInOrder())))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.removeProductFromOrder(1L, new EditeProductsInOrderRequestDto("name", 25L)))
                .isEqualTo(new OrderDto(List.of("")));

        verify(orderService).findByUserId(1L);
        verify(productInOrderService).findByOrderIdAndProductName(1L, "name");
        verify(productInOrderService).delete(productInOrder);
        verify(productService).update(new UpdateProductParams(
                3L,
                "pName",
                100L,
                4L,
                75L
        ));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testSubmitOrderWhenOrderNotFound() {
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.submitOrderByUserId(1L))
                .isEqualTo(new OrderDto(List.of("Not found order with user id - 1")));

        verify(orderService).findByUserId(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testSubmitOrderWhenProductInOrderListIsEmpty() {
        final Order order = new Order();
        order.setId(2L);
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.of(order));
        when(productInOrderService.findByOrderId(2L))
                .thenReturn(List.of());

        Assertions.assertThat(testSubject.submitOrderByUserId(1L))
                .isEqualTo(new OrderDto(List.of("Order with user id - 1 is empty")));

        verify(orderService).findByUserId(1L);
        verify(productInOrderService).findByOrderId(2L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testSubmitOrder() {
        final Order order = new Order();
        order.setId(2L);
        when(orderService.findByUserId(1L))
                .thenReturn(Optional.of(order));
        when(productInOrderService.findByOrderId(2L))
                .thenReturn(List.of(new ProductInOrder()));
        when(orderService.update(new UpdateOrderParams(
                2L,
                1L,
                OrderStatus.SUBMITTED
        ))).thenReturn(new Order());
        when(orderMapper.map(new Order(), List.of(new ProductInOrder())))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.submitOrderByUserId(1L))
                .isEqualTo(new OrderDto(List.of("")));

        verify(orderService).findByUserId(1L);
        verify(productInOrderService).findByOrderId(2L);
        verify(orderService).update(new UpdateOrderParams(
                2L,
                1L,
                OrderStatus.SUBMITTED
        ));
        verify(orderMapper).map(new Order(), List.of(new ProductInOrder()));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetUserByUserName() {
        when(userService.findByFirstNameAndLastName("first", "second"))
                .thenReturn(Optional.of(new User()));

        Assertions.assertThat(testSubject.getUserByUserName("first_second"))
                .isEqualTo(new User());

        verify(userService).findByFirstNameAndLastName("first", "second");

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                userService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }
}