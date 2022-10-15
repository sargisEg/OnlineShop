package org.shop.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PRODUCTS_IN_ORDERS")
public class ProductInOrder {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "PRODUCTS_IN_ORDERS_ID_SEQUENCE"
    )
    @SequenceGenerator(name = "PRODUCTS_IN_ORDERS_ID_SEQUENCE")
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_PRODUCTS_IN_ORDERS_ORDER_ID_ORDERS_ID")
    )
    private Order order;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_PRODUCTS_IN_ORDERS_PRODUCT_ID_PRODUCTS_ID")
    )
    private Product product;

    @Column(name = "count", nullable = false)
    private Long count;

    public ProductInOrder() {
    }

    public ProductInOrder(Order order, Product product, Long count) {
        this.order = order;
        this.product = product;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductInOrder{");
        sb.append("id=").append(id);
        sb.append(", order=").append(order);
        sb.append(", product=").append(product);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductInOrder that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(order, that.order) && Objects.equals(product, that.product) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, product, count);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
