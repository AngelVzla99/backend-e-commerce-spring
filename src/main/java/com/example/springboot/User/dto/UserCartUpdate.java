package com.example.springboot.User.dto;

import java.util.List;

public class UserCartUpdate {
    private List<CartItemDTO> products;

    public List<CartItemDTO> getProducts() {
        return products;
    }

    public void setProducts(List<CartItemDTO> products) {
        this.products = products;
    }
}
