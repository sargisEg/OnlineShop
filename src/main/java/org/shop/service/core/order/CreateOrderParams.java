package org.shop.service.core.order;

import org.shop.entity.OrderStatus;
import org.springframework.util.Assert;

import java.util.Objects;

public class CreateOrderParams {

    private final Long userId;

    private final OrderStatus orderStatus;

    public CreateOrderParams(Long userId, OrderStatus orderStatus) {
        Assert.notNull(
                userId,
                "Class - CreateOrderParams, method - constructor " +
                        "user id should not be null"
        );
        Assert.notNull(
                orderStatus,
                "Class - CreateOrderParams, method - constructor " +
                        "order status should not be null"
        );
        this.userId = userId;
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateOrderParams that)) return false;
        return Objects.equals(userId, that.userId) && orderStatus == that.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orderStatus);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateOrderParams{");
        sb.append("userId=").append(userId);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append('}');
        return sb.toString();
    }

    public Long getUserId() {
        return userId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
