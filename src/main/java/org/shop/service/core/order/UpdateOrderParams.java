package org.shop.service.core.order;

import org.shop.entity.OrderStatus;
import org.springframework.util.Assert;

import java.util.Objects;

public class UpdateOrderParams {

    private final Long id;

    private final Long userId;

    private final OrderStatus orderStatus;

    public UpdateOrderParams(Long id, Long userId, OrderStatus orderStatus) {
        Assert.notNull(
                id,
                "Class - UpdateOrderParams, method - constructor " +
                        "id should not be null"
        );
        Assert.notNull(
                userId,
                "Class - UpdateOrderParams, method - constructor " +
                        "user id should not be null"
        );
        Assert.notNull(
                orderStatus,
                "Class - UpdateOrderParams, method - constructor " +
                        "order status should not be null"
        );

        this.id = id;
        this.userId = userId;
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateOrderParams{");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateOrderParams that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && orderStatus == that.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, orderStatus);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
