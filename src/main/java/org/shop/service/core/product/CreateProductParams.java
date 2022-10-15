package org.shop.service.core.product;

import org.springframework.util.Assert;

import java.util.Objects;

public class CreateProductParams {

    private final String name;

    private final Long price;

    private final Long categoryId;

    private final Long count;

    public CreateProductParams(String name, Long price, Long categoryId, Long count) {
        Assert.hasText(
                name,
                "Class - CreateProductParams, method - constructor " +
                        "name should not be null or empty");
        Assert.notNull(
                price,
                "Class - CreateProductParams, method - constructor " +
                        "price should not be null");
        Assert.notNull(
                categoryId,
                "Class - CreateProductParams, method - constructor " +
                        "categoryId should not be null");
        Assert.notNull(
                count,
                "Class - CreateProductParams, method - constructor " +
                        "count should not be null");
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateProductParams{");
        sb.append("name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateProductParams that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(categoryId, that.categoryId) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, categoryId, count);
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
