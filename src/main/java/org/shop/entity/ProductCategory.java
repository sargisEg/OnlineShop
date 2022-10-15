package org.shop.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PRODUCT_CATEGORIES")
public class ProductCategory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "PRODUCT_CATEGORIES_SEQUENCE"
    )
    @SequenceGenerator(name = "PRODUCT_CATEGORIES_SEQUENCE")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    public ProductCategory() {
    }

    public ProductCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductCategory{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCategory that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
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
}
