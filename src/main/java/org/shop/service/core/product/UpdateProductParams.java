package org.shop.service.core.product;

import org.springframework.util.Assert;

import java.util.Objects;

public class UpdateProductParams {

    private final Long id;
    private final String name;

    private final Long price;

    private final Long categoryId;

    private final Long count;

    public UpdateProductParams(Long id, String name, Long price, Long categoryId, Long count) {
        Assert.notNull(
                id,
                "Class - UpdateProductParams, method - constructor " +
                        "id should not be null");
        Assert.hasText(
                name,
                "Class - UpdateProductParams, method - constructor " +
                        "name should not be null or empty");
        Assert.notNull(
                price,
                "Class - UpdateProductParams, method - constructor " +
                        "price should not be null");
        Assert.notNull(
                categoryId,
                "Class - UpdateProductParams, method - constructor " +
                        "categoryId should not be null");
        Assert.notNull(
                count,
                "Class - UpdateProductParams, method - constructor " +
                        "count should not be null");
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateProductParams{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateProductParams that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(categoryId, that.categoryId) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, categoryId, count);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getCount() {
        return count;
    }
}
