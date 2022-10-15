package org.shop.service.core.category;

import org.springframework.util.Assert;

import java.util.Objects;

public class CreateProductCategoryParams {

    private final String name;

    public CreateProductCategoryParams(String name) {
        Assert.hasText(
                name,
                "Class - CreateProductCategoryParams, method - constructor " +
                        "name should not be null or empty"
        );
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateProductCategoryParams{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateProductCategoryParams that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}
