package org.shop.facade.dto;

import java.util.List;
import java.util.Objects;

public class ProductDto {

    private Long id;
    private String name;
    private Long price;
    private ProductCategoryDto categoryDto;
    private Long count;

    private List<String> errors;

    public ProductDto(List<String> errors) {
        this.errors = errors;
    }

    public ProductDto(Long id, String name, Long price, ProductCategoryDto categoryDto, Long count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryDto = categoryDto;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", categoryDto=").append(categoryDto);
        sb.append(", count=").append(count);
        sb.append(", errors=").append(errors);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDto that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(categoryDto, that.categoryDto) && Objects.equals(count, that.count) && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, categoryDto, count, errors);
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public ProductCategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(ProductCategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
