package com.example.springboot.Order.entities;

import com.example.springboot.Product.entities.ProductEntity;
import jakarta.persistence.*;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderEntity order;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    private Integer quantity;

    @Override
    public String toString() {
        return "OrderItemEntity{" +
                "id=" + id +
                ", product=" + product +
                ", order=" + order +
                ", purchasePrice=" + purchasePrice +
                ", quantity=" + quantity +
                '}';
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}