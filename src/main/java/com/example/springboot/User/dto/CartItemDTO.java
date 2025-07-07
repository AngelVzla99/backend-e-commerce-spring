package com.example.springboot.User.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private Long id;
    private Long productId;
    private int amount;
    private Long userId = -1L;
}
