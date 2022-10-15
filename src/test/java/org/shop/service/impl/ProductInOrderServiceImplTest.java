package org.shop.service.impl;

import org.assertj.core.api.Assertions;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductInOrderServiceImplTest {

    private ProductInOrderService testSubject;

    @Mock
    private ProductInOrderRepository productInOrderRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void init() {
        testSubject = new ProductInOrderServiceImpl(
                productInOrderRepository,
                orderService,
                productService
        );
    }

    @Test
    public void testCreateWhenParamsIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.create(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateWhenParamsIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.update(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testDeleteWhenParamsIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.delete(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findById(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByOrderIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findByOrderId(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByOrderIdAndProductNameWhenOrderIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findByOrderIdAndProductName(null, "name");
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByOrderIdAndProductNameWhenOrderNameIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findByOrderIdAndProductName(10L, null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByOrderIdAndProductNameWhenOrderNameIsEmpty() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findByOrderIdAndProductName(10L, "");
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreateWhenOrderNotFound() {
        when(orderService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.create(new CreateProductInOrderParams(
                    1L,
                    1L,
                    10L
            ));
        }).isExactlyInstanceOf(OrderNotFoundException.class);

        verify(orderService).findById(1L);

        verifyNoMoreInteractions(
                productInOrderRepository,
                orderService,
                productService
        );
    }

    @Test
    public void testCreateWhenProductNotFound() {
        when(orderService.findById(1L))
                .thenReturn(Optional.of(new Order()));
        when(productService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.create(new CreateProductInOrderParams(
                    1L,
                    1L,
                    10L
            ));
        }).isExactlyInstanceOf(ProductNotFoundException.class);

        verify(orderService).findById(1L);
        verify(productService).findById(1L);

        verifyNoMoreInteractions(
                productInOrderRepository,
                orderService,
                productService
        );
    }

    @Test
    public void testCreate() {
        when(orderService.findById(1L))
                .thenReturn(Optional.of(new Order()));
        when(productService.findById(1L))
                .thenReturn(Optional.of(new Product()));
        when(productInOrderRepository.save(
                new ProductInOrder(new Order(), new Product(), 10L)
        )).thenReturn(new ProductInOrder());

        Assertions.assertThat(
                testSubject.create(new CreateProductInOrderParams(
                        1L,
                        1L,
                        10L
                ))
        ).isEqualTo(new ProductInOrder());

        verify(orderService).findById(1L);
        verify(productService).findById(1L);
        verify(productInOrderRepository).save(
                new ProductInOrder(new Order(), new Product(), 10L)
        );

        verifyNoMoreInteractions(
                productInOrderRepository,
                orderService,
                productService
        );
    }

    @Test
    public void testUpdateWhenProductInOrderNotFound() {
        when(productInOrderRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.update(new UpdateProductInOrderParams(
                    1L,
                    2L,
                    3L,
                    10L
            ));
        }).isExactlyInstanceOf(ProductInOrderNotFoundException.class);

        verify(productInOrderRepository).findById(1L);

        verifyNoMoreInteractions(
                productInOrderRepository,
                orderService,
                productService
        );
    }

    @Test
    public void testUpdateWhenOrderNotFound() {
        when(productInOrderRepository.findById(1L))
                .thenReturn(Optional.of(new ProductInOrder()));
        when(orderService.findById(2L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.update(new UpdateProductInOrderParams(
                    1L,
                    2L,
                    3L,
                    10L
            ));
        }).isExactlyInstanceOf(OrderNotFoundException.class);

        verify(productInOrderRepository).findById(1L);
        verify(orderService).findById(2L);

        verifyNoMoreInteractions(
                productInOrderRepository,
                orderService,
                productService
        );
    }

    @Test
    public void testUpdateWhenProductNotFound() {
        when(productInOrderRepository.findById(1L))
                .thenReturn(Optional.of(new ProductInOrder()));
        when(orderService.findById(2L))
                .thenReturn(Optional.of(new Order()));
        when(productService.findById(3L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.update(new UpdateProductInOrderParams(
                    1L,
                    2L,
                    3L,
                    10L
            ));
        }).isExactlyInstanceOf(ProductNotFoundException.class);

        verify(productInOrderRepository).findById(1L);
        verify(orderService).findById(2L);
        verify(productService).findById(3L);

        verifyNoMoreInteractions(
                productInOrderRepository,
                orderService,
                productService
        );
    }

    @Test
    public void testUpdate() {
        when(productInOrderRepository.findById(1L))
                .thenReturn(Optional.of(new ProductInOrder()));
        when(orderService.findById(2L))
                .thenReturn(Optional.of(new Order()));
        when(productService.findById(3L))
                .thenReturn(Optional.of(new Product()));

        Assertions.assertThat(
                testSubject.update(new UpdateProductInOrderParams(
                        1L,
                        2L,
                        3L,
                        10L
                ))
        ).isEqualTo(new ProductInOrder(new Order(), new Product(), 10L));

        verify(productInOrderRepository).findById(1L);
        verify(orderService).findById(2L);
        verify(productService).findById(3L);
        verify(productInOrderRepository).save(
                new ProductInOrder(
                        new Order(),
                        new Product(),
                        10L
                )
        );

        verifyNoMoreInteractions(
                productInOrderRepository,
                orderService,
                productService
        );
    }

    @Test
    public void testFindById() {
        when(productInOrderRepository.findById(1L))
                .thenReturn(Optional.of(new ProductInOrder()));

        Assertions.assertThat(testSubject.findById(1L))
                .isEqualTo(Optional.of(new ProductInOrder()));

        verify(productInOrderRepository).findById(1L);

        verifyNoMoreInteractions(
                productInOrderRepository,
                orderService,
                productService
        );
    }

    @Test
    public void testFindByOrderIdWhenOrderNotFound() {
        when(orderService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.findByOrderId(1L);
        }).isExactlyInstanceOf(OrderNotFoundException.class);

        verify(orderService).findById(1L);

        verifyNoMoreInteractions(
                productInOrderRepository,
                orderService,
                productService
        );
    }

    @Test
    public void testFindByOrderId() {
        when(orderService.findById(1L))
                .thenReturn(Optional.of(new Order()));
        when(productInOrderRepository.findByOrderId(1L))
                .thenReturn(List.of(new ProductInOrder()));

        Assertions.assertThat(testSubject.findByOrderId(1L))
                .isEqualTo(List.of(new ProductInOrder()));

        verify(orderService).findById(1L);
        verify(productInOrderRepository).findByOrderId(1L);

        verifyNoMoreInteractions(
                productInOrderRepository,
                orderService,
                productService
        );
    }

    @Test
    public void testFindByOrderIdAndProductName() {
        when(productInOrderRepository.findByOrderIdAndProductName(1L, "name"))
                .thenReturn(Optional.of(new ProductInOrder()));

        Assertions.assertThat(testSubject.findByOrderIdAndProductName(1L, "name"))
                .isEqualTo(Optional.of(new ProductInOrder()));

        verify(productInOrderRepository).findByOrderIdAndProductName(1L, "name");

        verifyNoMoreInteractions(
                productInOrderRepository,
                orderService,
                productService
        );
    }
}