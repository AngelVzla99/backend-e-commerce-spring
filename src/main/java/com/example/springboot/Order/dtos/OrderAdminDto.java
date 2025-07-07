package com.example.springboot.Order.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderAdminDto {
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
    private List<OrderItemAdminDto> orderItems;
    @NotNull(message = "userId is required")
    private Long userId;
    @NotNull(message = "addressId is required")
    private Long addressId;
    @NotNull(message = "paymentMethods is required")
    @Size(min = 1, message = "paymentMethods should have at least one method")
    @Valid
    private List<PaymentMethodDTO> paymentMethods;
}
