package org.shop.facade.dto;

import java.util.Objects;

public class ProductInOrderDto {

    private Long id;

    private ProductDto productDto;

    private Long count;

    public ProductInOrderDto(Long id, ProductDto productDto, Long count) {
        this.id = id;
        this.productDto = productDto;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductInOrderDto{");
        sb.append("id=").append(id);
        sb.append(", productDto=").append(productDto);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductInOrderDto that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(productDto, that.productDto) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productDto, count);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
