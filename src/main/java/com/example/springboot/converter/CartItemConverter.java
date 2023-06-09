package com.example.springboot.converter;

import com.example.springboot.dto.CartItemDTO;
import com.example.springboot.model.CartItemEntity;
import com.example.springboot.model.ProductEntity;
import com.example.springboot.model.UserEntity;
import com.example.springboot.repository.ProductEntityRepository;
import com.example.springboot.repository.UserEntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartItemConverter {
    private final UserEntityRepository userRepository;
    private final ProductEntityRepository productRepository;

    public CartItemConverter(UserEntityRepository userRepository, ProductEntityRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public CartItemDTO toDTO(CartItemEntity cartItemEntity){
        CartItemDTO cartItem = new CartItemDTO();
        cartItem.setAmount(cartItemEntity.getAmount());
        cartItem.setProductId(cartItemEntity.getProduct().getId());
        cartItem.setUserId(cartItemEntity.getUser().getId());
        return cartItem;
    }

    public CartItemEntity toEntity(CartItemDTO cartItemDTO){
        CartItemEntity cartItem = new CartItemEntity();
        cartItem.setAmount(cartItemDTO.getAmount());
        // get product from database
        ProductEntity product = productRepository
                .findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The product with id "+cartItemDTO.getProductId() +" was not found"));
        cartItem.setProduct( product );
        // get user from database
        UserEntity user = userRepository
                .findById(cartItemDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with id "+cartItemDTO.getUserId() +" was not found"));
        cartItem.setUser(user);
        return cartItem;
    }

    public List<CartItemEntity> toEntityList(List<CartItemDTO> listCartItemDTO){
        return listCartItemDTO
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<CartItemDTO> toDtoList(List<CartItemEntity> listCartItemDTO){
        return listCartItemDTO
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
