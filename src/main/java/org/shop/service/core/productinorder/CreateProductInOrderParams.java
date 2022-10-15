package org.shop.service.core.productinorder;

import org.springframework.util.Assert;

import java.util.Objects;

public class CreateProductInOrderParams {

    private final Long orderId;

    private final Long productId;

    private final Long count;

    public CreateProductInOrderParams(Long orderId, Long productId, Long count) {
        Assert.notNull(
                orderId,
                "Class - CreateProductInOrderParams, method - constructor " +
                        "order id should not be null"
        );
        Assert.notNull(
                productId,
                "Class - CreateProductInOrderParams, method - constructor " +
                        "product id should not be null"
        );
        Assert.notNull(
                count,
                "Class - CreateProductInOrderParams, method - constructor " +
                        "count should not be null"
        );
        this.orderId = orderId;
        this.productId = productId;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateProductInOrderParams{");
        sb.append("orderId=").append(orderId);
        sb.append(", productId=").append(productId);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateProductInOrderParams that)) return false;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, count);
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
