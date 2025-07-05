package com.example.springboot.User.services;

import com.example.springboot.Order.converters.OrderConverter;
import com.example.springboot.Order.dtos.OrderDTO;
import com.example.springboot.User.converter.AddressConverter;
import com.example.springboot.User.converter.CartItemConverter;
import com.example.springboot.User.dto.AddressDTO;
import com.example.springboot.User.dto.CartItemDTO;
import com.example.springboot.User.dto.UserDTO;
import com.example.springboot.User.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    // services
    @Autowired
    UserService userService;
    @Autowired
    AddressService addressService;
    @Autowired
    CartItemService cartItemService;
    // converters
    @Autowired
    AddressConverter addressConverter;
    @Autowired
    CartItemConverter cartItemConverter;
    @Autowired
    OrderConverter orderConverter;

    public UserDTO create(UserDTO dto){
        return userService.createCustomer(dto);
    }

    public UserDTO findMe(String email){
        return userService.findByEmail(email);
    }

    public AddressDTO createAddress(AddressDTO dto){
        return addressService.save(dto);
    }

    public List<AddressDTO> findAddresses(String email){
        UserEntity user = userService.findUserEntityByEmail(email);
        return addressConverter.toDtoList(user.getAddresses());
    }

    public CartItemDTO createCartItem(CartItemDTO dto){
        return cartItemService.save(dto);
    }

    public List<CartItemDTO> findCartItems(String email){
        UserEntity user = userService.findUserEntityByEmail(email);
        return cartItemConverter.toDtoList(user.getCartItems());
    }

    public List<OrderDTO> findOrders(String email){
        UserEntity user = userService.findUserEntityByEmail(email);
        return orderConverter.toDtoList(user.getOrders());
    }

    public void deleteAddress(Long userId, Long addressId){
        addressService.delete(userId,addressId);
    }

    public void deleteCartItem(UserEntity user, Long cartItemId){
        cartItemService.deleteUserCartItem(user,cartItemId);
    }
}
