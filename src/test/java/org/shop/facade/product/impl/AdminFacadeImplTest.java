package org.shop.facade.product.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminFacadeImplTest {

    private AdminFacade testSubject;

    @Mock
    private ProductService productService;
    @Mock
    private ProductCategoryService productCategoryService;
    @Mock
    private OrderService orderService;
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
        testSubject = new AdminFacadeImpl(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testCreateCategoryWhenDtoIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.createCategory(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testCreateProductWhenDtoIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.createProduct(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testEditeProductWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.editeProduct(null, new SaveProductRequestDto());
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testEditeProductWhenDtoIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.editeProduct(10L, null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testDeleteProductWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.deleteProduct(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetOrderByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.getOrderById(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testApproveOrderWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.approveOrder(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testDeclineOrderWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.approveOrder(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testCreateCategoryWhenExistWithSameName() {
        when(productCategoryService.findByName("name"))
                .thenReturn(Optional.of(new ProductCategory()));

        Assertions.assertThat(testSubject.createCategory(
                new SaveProductCategoryRequestDto("name")
        )).isEqualTo(new ProductCategoryDto(List.of("Category with name - name already exist")));

        verify(productCategoryService).findByName("name");

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testCreateCategory() {
        when(productCategoryService.findByName("name"))
                .thenReturn(Optional.empty());
        when(productCategoryService.create(new CreateProductCategoryParams("name")))
                .thenReturn(new ProductCategory());
        when(productCategoryMapper.map(new ProductCategory()))
                .thenReturn(new ProductCategoryDto(List.of("")));

        Assertions.assertThat(testSubject.createCategory(
                new SaveProductCategoryRequestDto("name")
        )).isEqualTo(new ProductCategoryDto(List.of("")));

        verify(productCategoryService).findByName("name");
        verify(productCategoryService).create(new CreateProductCategoryParams("name"));
        verify(productCategoryMapper).map(new ProductCategory());

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testCreateProductWhenExistWithSameName() {
        when(productService.findByName("name"))
                .thenReturn(Optional.of(new Product()));

        Assertions.assertThat(testSubject.createProduct(
                new SaveProductRequestDto("name", 10L, "cName", 10L)
        )).isEqualTo(new ProductDto(List.of("Product with name - name already exist")));

        verify(productService).findByName("name");

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testCreateProductWhenCategoryNotFound() {
        when(productService.findByName("name"))
                .thenReturn(Optional.empty());
        when(productCategoryService.findByName("cName"))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.createProduct(
                new SaveProductRequestDto("name", 10L, "cName", 10L)
        )).isEqualTo(new ProductDto(List.of("Not found category with name - cName")));

        verify(productService).findByName("name");
        verify(productCategoryService).findByName("cName");

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testCreateProduct() {
        when(productService.findByName("name"))
                .thenReturn(Optional.empty());

        final ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1L);
        when(productCategoryService.findByName("cName"))
                .thenReturn(Optional.of(productCategory));

        when(productService.create(new CreateProductParams(
                "name",
                10L,
                1L,
                10L
        ))).thenReturn(new Product());
        when(productMapper.map(new Product()))
                .thenReturn(new ProductDto(List.of("")));

        Assertions.assertThat(testSubject.createProduct(
                new SaveProductRequestDto("name", 10L, "cName", 10L)
        )).isEqualTo(new ProductDto(List.of("")));

        verify(productService).findByName("name");
        verify(productCategoryService).findByName("cName");
        verify(productService).create(new CreateProductParams(
                "name",
                10L,
                1L,
                10L
        ));
        verify(productMapper).map(new Product());

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testEditeProductWhenProductNotFound() {
        when(productService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.editeProduct(1L, new SaveProductRequestDto()))
                .isEqualTo(new ProductDto(List.of("Not found product with id - 1")));

        verify(productService).findById(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testEditeProductWhenProductNameExist() {
        when(productService.findById(1L))
                .thenReturn(Optional.of(new Product()));
        final Product product = new Product();
        product.setId(1L);
        when(productService.findByName("name"))
                .thenReturn(Optional.of(product));

        Assertions.assertThat(testSubject.editeProduct(
                1L,
                new SaveProductRequestDto("name", 10L, "cName", 10L)
        )).isEqualTo(new ProductDto(List.of("Cannot change product name product with name - name already exist")));

        verify(productService).findById(1L);
        verify(productService).findByName("name");

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testEditeProductWhenCategoryNotFound() {
        when(productService.findById(1L))
                .thenReturn(Optional.of(new Product()));
        when(productService.findByName("name"))
                .thenReturn(Optional.empty());
        when(productCategoryService.findByName("cName"))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.editeProduct(
                1L,
                new SaveProductRequestDto("name", 10L, "cName", 10L)
        )).isEqualTo(new ProductDto(List.of("Not found category with name - cName")));

        verify(productService).findById(1L);
        verify(productService).findByName("name");
        verify(productCategoryService).findByName("cName");

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testEditeProduct() {
        final Product product = new Product();
        product.setId(1L);

        when(productService.findById(1L))
                .thenReturn(Optional.of(product));

        when(productService.findByName("name"))
                .thenReturn(Optional.empty());

        final ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1L);
        when(productCategoryService.findByName("cName"))
                .thenReturn(Optional.of(productCategory));

        when(productService.update(
                new UpdateProductParams(
                        1L,
                        "name",
                        10L,
                        1L,
                        10L
                )
        )).thenReturn(new Product());

        when(productMapper.map(new Product()))
                .thenReturn(new ProductDto(List.of("")));

        Assertions.assertThat(testSubject.editeProduct(
                1L,
                new SaveProductRequestDto("name", 10L, "cName", 10L)
        )).isEqualTo(new ProductDto(List.of("")));

        verify(productService).findById(1L);
        verify(productService).findByName("name");
        verify(productCategoryService).findByName("cName");
        verify(productService).update(new UpdateProductParams(
                1L,
                "name",
                10L,
                1L,
                10L
        ));
        verify(productMapper).map(new Product());

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testDeleteProductWhenProductNotFound() {
        when(productService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.deleteProduct(1L))
                .isEqualTo(new ProductDto(List.of("Not found product with id - 1")));

        verify(productService).findById(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testDeleteProduct() {
        when(productService.findById(1L))
                .thenReturn(Optional.of(new Product()));
        when(productMapper.map(new Product()))
                .thenReturn(new ProductDto(List.of("")));

        Assertions.assertThat(testSubject.deleteProduct(1L))
                .isEqualTo(new ProductDto(List.of("")));

        verify(productService).findById(1L);
        verify(productMapper).map(new Product());
        verify(productService).delete(new Product());

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetOrders() {
        when(orderService.findAll())
                .thenReturn(List.of(new Order()));
        when(productInOrderService.findByOrderId(null))
                .thenReturn(List.of(new ProductInOrder()));
        when(orderMapper.map(new Order(), List.of(new ProductInOrder())))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.getOrders())
                .isEqualTo(List.of(new OrderDto(List.of(""))));

        verify(orderService).findAll();
        verify(productInOrderService).findByOrderId(null);
        verify(orderMapper).map(new Order(), List.of(new ProductInOrder()));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetOrderByIdWhenOrderNotFound() {
        when(orderService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.getOrderById(1L))
                .isEqualTo(new OrderDto(List.of("Not found order with id - 1")));

        verify(orderService).findById(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetOrderById() {
        when(orderService.findById(1L))
                .thenReturn(Optional.of(new Order()));
        when(productInOrderService.findByOrderId(1L))
                .thenReturn(List.of(new ProductInOrder()));
        when(orderMapper.map(new Order(), List.of(new ProductInOrder())))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.getOrderById(1L))
                .isEqualTo(new OrderDto(List.of("")));

        verify(orderService).findById(1L);
        verify(productInOrderService).findByOrderId(1L);
        verify(orderMapper).map(new Order(), List.of(new ProductInOrder()));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testApproveOrderWhenOrderNotFound() {
        when(orderService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.approveOrder(1L))
                .isEqualTo(new OrderDto(List.of("Not found order with orderId - 1")));

        verify(orderService).findById(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testApproveOrderWhenOrderNotSubmitted() {
        when(orderService.findById(1L))
                .thenReturn(Optional.of(new Order(new User(), OrderStatus.WAITING)));

        Assertions.assertThat(testSubject.approveOrder(1L))
                .isEqualTo(new OrderDto(List.of("Order with id - 1 was not submitted")));

        verify(orderService).findById(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testApproveOrder() {
        when(orderService.findById(1L))
                .thenReturn(Optional.of(new Order(new User(), OrderStatus.SUBMITTED)));
        when(productInOrderService.findByOrderId(1L))
                .thenReturn(List.of(new ProductInOrder()));
        when(orderMapper.map(new Order(new User(), OrderStatus.SUBMITTED), List.of(new ProductInOrder())))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.approveOrder(1L))
                .isEqualTo(new OrderDto(List.of("")));

        verify(orderService).findById(1L);
        verify(productInOrderService).findByOrderId(1L);
        verify(productInOrderService).delete(new ProductInOrder());
        verify(orderService).delete(new Order(new User(), OrderStatus.SUBMITTED));
        verify(orderMapper).map(new Order(new User(), OrderStatus.SUBMITTED), List.of(new ProductInOrder()));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testDeclineOrderWhenOrderNotFound() {
        when(orderService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.declineOrder(1L))
                .isEqualTo(new OrderDto(List.of("Not found order with orderId - 1")));

        verify(orderService).findById(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testDeclineOrderWhenOrderNotSubmitted() {
        when(orderService.findById(1L))
                .thenReturn(Optional.of(new Order(new User(), OrderStatus.WAITING)));

        Assertions.assertThat(testSubject.declineOrder(1L))
                .isEqualTo(new OrderDto(List.of("Order with orderId - 1 was not submitted")));

        verify(orderService).findById(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testDeclineOrder() {
        when(orderService.findById(1L))
                .thenReturn(Optional.of(new Order(new User(), OrderStatus.SUBMITTED)));

        final ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1L);
        final Product product = new Product(
                "name",
                10L,
                productCategory,
                10L
        );
        product.setId(1L);
        final ProductInOrder productInOrder = new ProductInOrder(
                new Order(),
                product,
                10L
        );
        when(productInOrderService.findByOrderId(1L))
                .thenReturn(List.of(productInOrder));

        when(orderMapper.map(new Order(new User(), OrderStatus.SUBMITTED), List.of(productInOrder)))
                .thenReturn(new OrderDto(List.of("")));

        Assertions.assertThat(testSubject.declineOrder(1L))
                .isEqualTo(new OrderDto(List.of("")));

        verify(orderService).findById(1L);
        verify(productInOrderService).findByOrderId(1L);
        verify(productInOrderService).delete(productInOrder);
        verify(productService).update(new UpdateProductParams(
                1L,
                "name",
                10L,
                1L,
                20L
        ));
        verify(orderService).delete(new Order(new User(), OrderStatus.SUBMITTED));
        verify(orderMapper).map(new Order(new User(), OrderStatus.SUBMITTED), List.of(productInOrder));

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetProducts() {
        when(productService.findAll())
                .thenReturn(List.of(new Product()));
        when(productMapper.map(new Product()))
                .thenReturn(new ProductDto(List.of("")));

        Assertions.assertThat(testSubject.getProducts())
                .isEqualTo(List.of(new ProductDto(List.of(""))));

        verify(productService).findAll();
        verify(productMapper).map(new Product());

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetCategories() {
        when(productCategoryService.findAll())
                .thenReturn(List.of(new ProductCategory()));
        when(productCategoryMapper.map(new ProductCategory()))
                .thenReturn(new ProductCategoryDto(List.of("")));

        Assertions.assertThat(testSubject.getCategories())
                .isEqualTo(List.of(new ProductCategoryDto(List.of(""))));

        verify(productCategoryService).findAll();
        verify(productCategoryMapper).map(new ProductCategory());

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetProductsByCategoryWhenCategoryNotFound() {
        when(productCategoryService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.getProductsByCategory(1L))
                .isEqualTo(List.of(new ProductDto(List.of("Not found category with id - 1"))));

        verify(productCategoryService).findById(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetProductsByCategory() {
        when(productCategoryService.findById(1L))
                .thenReturn(Optional.of(new ProductCategory()));
        when(productService.findByCategoryId(1L))
                .thenReturn(List.of(new Product()));
        when(productMapper.map(new Product()))
                .thenReturn(new ProductDto(List.of("")));

        Assertions.assertThat(testSubject.getProductsByCategory(1L))
                .isEqualTo(List.of(new ProductDto(List.of(""))));

        verify(productCategoryService).findById(1L);
        verify(productService).findByCategoryId(1L);
        verify(productMapper).map(new Product());

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetProductByIdWhenProductNotFound() {
        when(productService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThat(testSubject.getProductById(1L))
                .isEqualTo(new ProductDto(List.of("Not found product with id - 1")));

        verify(productService).findById(1L);

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }

    @Test
    public void testGetProductById() {
        when(productService.findById(1L))
                .thenReturn(Optional.of(new Product()));
        when(productMapper.map(new Product()))
                .thenReturn(new ProductDto(List.of("")));

        Assertions.assertThat(testSubject.getProductById(1L))
                .isEqualTo(new ProductDto(List.of("")));

        verify(productService).findById(1L);
        verify(productMapper).map(new Product());

        verifyNoMoreInteractions(
                productService,
                productCategoryService,
                orderService,
                productInOrderService,
                productMapper,
                productCategoryMapper,
                orderMapper
        );
    }
}