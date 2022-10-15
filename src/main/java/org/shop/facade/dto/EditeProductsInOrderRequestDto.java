package org.shop.facade.dto;

import java.util.Objects;

public class EditeProductsInOrderRequestDto {

    private String productName;

    private Long productCount;

    public EditeProductsInOrderRequestDto(String productName, Long productCount) {
        this.productName = productName;
        this.productCount = productCount;
    }

    public EditeProductsInOrderRequestDto() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EditeProductInOrderRequestDto{");
        sb.append("productName='").append(productName).append('\'');
        sb.append(", productCount=").append(productCount);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EditeProductsInOrderRequestDto that)) return false;
        return Objects.equals(productName, that.productName) && Objects.equals(productCount, that.productCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productCount);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductCount() {
        return productCount;
    }

    public void setProductCount(Long productCount) {
        this.productCount = productCount;
    }
}
