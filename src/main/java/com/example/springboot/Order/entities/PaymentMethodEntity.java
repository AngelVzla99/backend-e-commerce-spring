package com.example.springboot.Order.entities;

import com.example.springboot.User.entities.UserEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "payment_method")
public class PaymentMethodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "payment_user_id")
    private String paymentUserId;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "payed_at")
    private Date payedAt;

    @Column(name = "expire_at")
    private Date expireAt;

    @Column(name = "isconfirmed", nullable = false)
    private Boolean isConfirmed;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "currency", nullable = false)
    private String currency;

    public PaymentMethodEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPaymentUserId() {
        return paymentUserId;
    }

    public void setPaymentUserId(String paymentUserId) {
        this.paymentUserId = paymentUserId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPayedAt() {
        return payedAt;
    }

    public void setPayedAt(Date payedAt) {
        this.payedAt = payedAt;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}