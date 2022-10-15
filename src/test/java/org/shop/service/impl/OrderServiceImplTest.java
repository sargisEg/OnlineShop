package org.shop.service.impl;

import org.assertj.core.api.Assertions;
import org.shop.entity.Order;
import org.shop.entity.OrderStatus;
import org.shop.entity.User;
import org.shop.repository.OrderRepository;
import org.shop.service.core.order.CreateOrderParams;
import org.shop.service.core.order.OrderService;
import org.shop.service.core.order.UpdateOrderParams;
import org.shop.service.core.user.UserService;
import org.shop.service.impl.exceptions.OrderNotFoundException;
import org.shop.service.impl.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private OrderService testSubject;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        testSubject = new OrderServiceImpl(orderRepository, userService);
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
    public void testFindByUserIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> {
            testSubject.findByUserId(null);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreateWhenUserNotFound() {
        when(userService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.create(new CreateOrderParams(1L, OrderStatus.WAITING));
        }).isExactlyInstanceOf(UserNotFoundException.class);

        verify(userService).findById(1L);

        verifyNoMoreInteractions(orderRepository, userService);
    }

    @Test
    public void testCreate() {
        when(userService.findById(1L))
                .thenReturn(Optional.of(new User()));
        when(orderRepository.save(new Order(new User(), OrderStatus.WAITING)))
                .thenReturn(new Order());

        Assertions.assertThat(testSubject.create(new CreateOrderParams(1L, OrderStatus.WAITING)))
                .isEqualTo(new Order());

        verify(userService).findById(1L);
        verify(orderRepository).save(new Order(new User(), OrderStatus.WAITING));

        verifyNoMoreInteractions(orderRepository, userService);
    }

    @Test
    public void testUpdateWhenOrderNotFound() {
        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.update(new UpdateOrderParams(1L, 1L, OrderStatus.WAITING));
        }).isExactlyInstanceOf(OrderNotFoundException.class);

        verify(orderRepository).findById(1L);

        verifyNoMoreInteractions(orderRepository, userService);
    }

    @Test
    public void testUpdateWhenUserNotFound() {
        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(new Order()));
        when(userService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.update(new UpdateOrderParams(1L, 1L, OrderStatus.WAITING));
        }).isExactlyInstanceOf(UserNotFoundException.class);

        verify(orderRepository).findById(1L);
        verify(userService).findById(1L);

        verifyNoMoreInteractions(orderRepository, userService);
    }

    @Test
    public void testUpdate() {
        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(new Order()));
        when(userService.findById(1L))
                .thenReturn(Optional.of(new User()));

        Assertions.assertThat(testSubject.update(new UpdateOrderParams(1L, 1L, OrderStatus.WAITING)))
                        .isEqualTo(new Order(new User(), OrderStatus.WAITING));

        verify(orderRepository).findById(1L);
        verify(userService).findById(1L);
        verify(orderRepository).save(new Order(new User(), OrderStatus.WAITING));

        verifyNoMoreInteractions(orderRepository, userService);
    }

    @Test
    public void testFindById() {
        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(new Order()));

        Assertions.assertThat(testSubject.findById(1L))
                .isEqualTo(Optional.of(new Order()));

        verify(orderRepository).findById(1L);

        verifyNoMoreInteractions(orderRepository, userService);
    }

    @Test
    public void findByUserIdWhenUserNotFound() {
        when(userService.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            testSubject.findByUserId(1L);
        }).isExactlyInstanceOf(UserNotFoundException.class);

        verify(userService).findById(1L);

        verifyNoMoreInteractions(orderRepository, userService);
    }

    @Test
    public void findByUserId() {
        when(userService.findById(1L))
                .thenReturn(Optional.of(new User()));
        when(orderRepository.findByUserId(1L))
                .thenReturn(Optional.of(new Order()));

        Assertions.assertThat(testSubject.findByUserId(1L))
                        .isEqualTo(Optional.of(new Order()));

        verify(userService).findById(1L);
        verify(orderRepository).findByUserId(1L);

        verifyNoMoreInteractions(orderRepository, userService);
    }

    @Test
    public void testFindAll() {
        when(orderRepository.findAll())
                .thenReturn(List.of(new Order()));

        Assertions.assertThat(testSubject.findAll())
                .isEqualTo(List.of(new Order()));

        verify(orderRepository).findAll();

        verifyNoMoreInteractions(orderRepository, userService);
    }
}