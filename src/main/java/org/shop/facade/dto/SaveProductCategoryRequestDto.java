package org.shop.facade.dto;

import java.util.Objects;

public class SaveProductCategoryRequestDto {

    private String name;

    public SaveProductCategoryRequestDto() {
    }

    public SaveProductCategoryRequestDto(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SaveProductCategoryRequest{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaveProductCategoryRequestDto that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
