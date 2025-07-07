package com.example.springboot.Order.dtos;

import com.example.springboot.Product.dtos.ProductDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemAdminDto {
    private ProductDTO product;
    private BigDecimal purchasePrice;
    private Integer quantity;
}
