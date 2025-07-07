package com.example.springboot.User.services;

import com.example.springboot.User.converter.CartItemConverter;
import com.example.springboot.User.dto.CartItemDTO;
import com.example.springboot.User.entities.AddressEntity;
import com.example.springboot.User.entities.CartItemEntity;
import com.example.springboot.User.entities.UserEntity;
import com.example.springboot.User.repositories.CartItemEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CartItemService {
    @Autowired
    private CartItemEntityRepository cartItemRepository;
    @Autowired
    private CartItemConverter cartItemConverter;

    public List<CartItemDTO> saveAll(List<CartItemDTO> cartItemDTOs) {
        List<CartItemEntity> entities = cartItemConverter.toEntityList(cartItemDTOs);
        return cartItemConverter.toDtoList( cartItemRepository.saveAll(entities) );
    }

    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }

    public CartItemDTO save(CartItemDTO dto){
        CartItemEntity entity = cartItemConverter.toEntity(dto);
        CartItemEntity newEntity = cartItemRepository.save(entity);
        return cartItemConverter.toDTO(newEntity);
    }

    public void deleteUserCartItem(UserEntity user, Long cartItemId){
        // search address in the db
//        cartItemRepository
//            .deleteByUserAndId(user,cartItemId)
//            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item don't belong to the user of the token, or not exist"));
        cartItemRepository.deleteById(cartItemId);
    }
}
