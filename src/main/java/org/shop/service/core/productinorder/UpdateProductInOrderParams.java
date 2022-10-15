package org.shop.service.core.productinorder;

import org.springframework.util.Assert;

import java.util.Objects;

public class UpdateProductInOrderParams {

    private final Long id;

    private final Long orderId;

    private final Long productId;

    private final Long count;

    public UpdateProductInOrderParams(Long id, Long orderId, Long productId, Long count) {
        Assert.notNull(
                id,
                "Class - UpdateProductInOrderParams, method - constructor " +
                        " id should not be null"
        );
        Assert.notNull(
                orderId,
                "Class - UpdateProductInOrderParams, method - constructor " +
                        "order id should not be null"
        );
        Assert.notNull(
                productId,
                "Class - UpdateProductInOrderParams, method - constructor " +
                        "product id should not be null"
        );
        Assert.notNull(
                count,
                "Class - UpdateProductInOrderParams, method - constructor " +
                        "count should not be null"
        );
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateProductInOrderParams{");
        sb.append("id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", productId=").append(productId);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateProductInOrderParams that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, productId, count);
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getCount() {
        return count;
    }
}
