package org.shop.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "PRODUCT_ID_SEQUENCE")
    @SequenceGenerator(name = "PRODUCT_ID_SEQUENCE")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(
            name = "category",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_PRODUCTS_CATEGORY_PRODUCT_CATEGORIES_ID")
    )
    private ProductCategory category;

    @Column(name = "count", nullable = false)
    private Long count;

    public Product() {
    }

    public Product(String name, Long price, ProductCategory category, Long count) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", category=").append(category);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(category, product.category) && Objects.equals(count, product.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, category, count);
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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
