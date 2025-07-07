package com.example.springboot.Product.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "product_discount")
public class ProductDiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name="expire_at")
    private Date expireAt;

    @Column(name="discount_percentage")
    private Integer discountPercentage;

    @Column
    private Boolean active;

    @Override
    public String toString() {
        return "ProductDiscountEntity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", expireAt=" + expireAt +
                ", discountPercentage=" + discountPercentage +
                ", active=" + active +
                '}';
    }

    // constructors

    public ProductDiscountEntity() {}

    public ProductDiscountEntity(Long id, Date createdAt, Date expireAt, Integer discountPercentage, Boolean active) {
        this.id = id;
        this.createdAt = createdAt;
        this.expireAt = expireAt;
        this.discountPercentage = discountPercentage;
        this.active = active;
    }

    public ProductDiscountEntity(Date createdAt, Date expireAt, Integer discountPercentage, Boolean active) {
        this.createdAt = createdAt;
        this.expireAt = expireAt;
        this.discountPercentage = discountPercentage;
        this.active = active;
    }

    public ProductDiscountEntity(Long id, Date expireAt, Integer discountPercentage, Boolean active) {
        this.id = id;
        this.expireAt = expireAt;
        this.discountPercentage = discountPercentage;
        this.active = active;
    }

    // getters ans setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}