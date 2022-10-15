package org.shop.facade.dto;

import java.util.List;
import java.util.Objects;

public class ProductCategoryDto {

    private Long id;
    private String name;

    private List<String> errors;

    public ProductCategoryDto(List<String> errors) {
        this.errors = errors;
    }

    public ProductCategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductCategoryDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", errors=").append(errors);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCategoryDto that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, errors);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
