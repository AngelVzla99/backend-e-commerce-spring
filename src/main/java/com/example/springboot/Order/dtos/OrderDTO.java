package com.example.springboot.Order.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Null;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    @Null(message = "The id should be null")
    private Long id;
    @Null(message = "createdAt should be null")
    private Date createdAt;
    @NotNull(message = "statusCode is required")
    @Min(value = 0, message = "status should be zero")
    @Max(value = 0, message = "status should be zero")
    private Integer statusCode;
    @NotNull(message = "orderItems is required")
    @Size(min = 1, message = "orderItems should have at least one item")
    private List<OrderItemDTO> orderItems;
    @NotNull(message = "userId is required")
    private Long userId;
    @NotNull(message = "addressId is required")
    private Long addressId;
    @NotNull(message = "paymentMethods is required")
    @Size(min = 1, message = "paymentMethods should have at least one method")
    @Valid
    private List<PaymentMethodDTO> paymentMethods;

    public OrderDTO() {}

    // getters and setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

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

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public List<PaymentMethodDTO> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethodDTO> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
