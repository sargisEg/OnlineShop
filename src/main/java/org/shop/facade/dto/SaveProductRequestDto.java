package org.shop.facade.dto;

import java.util.Objects;

public class SaveProductRequestDto {

    private String name;
    private Long price;
    private String categoryName;
    private Long count;

    public SaveProductRequestDto() {
    }

    public SaveProductRequestDto(String name,
                                 Long price,
                                 String categoryName,
                                 Long count) {
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SaveProductRequestDto{");
        sb.append("name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", categoryName='").append(categoryName).append('\'');
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaveProductRequestDto that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(categoryName, that.categoryName) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, categoryName, count);
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
