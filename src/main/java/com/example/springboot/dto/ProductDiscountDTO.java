package com.example.springboot.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

public class ProductDiscountDTO {
    private Long id;
    private Date expireAt;
    private Integer discountPercentage;
    private Boolean active;

    @Override
    public String toString() {
        return "ProductDiscountDTO{" +
                "id=" + id +
                ", expireAt=" + expireAt +
                ", discountPercentage=" + discountPercentage +
                ", active=" + active +
                '}';
    }

    // constructors

    public ProductDiscountDTO() {}

    public ProductDiscountDTO(Date expireAt, Integer discountPercentage, Boolean active) {
        this.expireAt = expireAt;
        this.discountPercentage = discountPercentage;
        this.active = active;
    }

    public ProductDiscountDTO(Long id, Date expireAt, Integer discountPercentage, Boolean active) {
        this.id = id;
        this.expireAt = expireAt;
        this.discountPercentage = discountPercentage;
        this.active = active;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
