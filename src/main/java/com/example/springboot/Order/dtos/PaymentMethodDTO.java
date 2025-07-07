package com.example.springboot.Order.dtos;

import jakarta.validation.constraints.*;

import java.util.Date;

public class PaymentMethodDTO {
    @NotNull(message = "type is required")
    @Pattern(regexp = "^(cash|pagoMovil)$")
    private String type;

    @NotNull(message = "amount is required")
    @Positive(message = "amount should be positive")
    private Long amount;

    @NotNull(message = "paymentUserId is required")
    private String paymentUserId;

    private String paymentId;

    private Date payedAt;

    private Date expireAt;

    @NotNull(message = "isConfirmed is required")
    @AssertFalse(message = "The confirmation should be false, because the validation will be made internally")
    private Boolean isConfirmed;

    private String description = "";

    @NotNull(message = "currency is required")
    @Pattern(regexp = "^(USD|BS)$")
    private String currency;

    public PaymentMethodDTO() {}

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
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
