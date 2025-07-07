package com.example.springboot.User.controllers;

import com.example.springboot.Order.dtos.OrderDTO;
import com.example.springboot.User.dto.AddressDTO;
import com.example.springboot.User.dto.CartItemDTO;
import com.example.springboot.User.dto.UserDTO;
import com.example.springboot.User.entities.UserEntity;
import com.example.springboot.User.services.CustomerService;
import com.example.springboot.User.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    UserService userService;

    // ===============
    //    get EPs   //
    // ===============

    @GetMapping("/me")
    @ResponseBody
    public UserDTO me(Principal principal) {
        System.out.println("=>"+principal.getName());
        return customerService.findMe(principal.getName());
    }

    @GetMapping("/addresses")
    @ResponseBody
    public List<AddressDTO> myAddresses(Principal principal) {
        return customerService.findAddresses(principal.getName());
    }

    @GetMapping("/cart-items")
    @ResponseBody
    public List<CartItemDTO> myCartItems(Principal principal) {
        return customerService.findCartItems(principal.getName());
    }

    @GetMapping("/orders")
    @ResponseBody
    public List<OrderDTO> myOrders(Principal principal) {
        return customerService.findOrders(principal.getName());
    }

    // ===============
    //   post EPs   //
    // ===============

    @PostMapping
    @ResponseBody
    public UserDTO createCustomer(@Valid @RequestBody UserDTO dto) {
        if(dto.getId()!=null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This endpoint it's only to create a customer");
        }
        return customerService.create(dto);
    }

    @PostMapping("/address")
    @ResponseBody
    public AddressDTO createAddress(@Valid @RequestBody AddressDTO dto, Principal principal) {
        // TODO: Create a CreateAddressDTO to avoid updates in this EP
        UserDTO user = userService.findByEmailToken(principal.getName());
        dto.setUserId(user.getId());
        return customerService.createAddress(dto);
    }

    @PostMapping("/cart-item")
    @ResponseBody
    public CartItemDTO createCartItem(@Valid @RequestBody CartItemDTO dto, Principal principal) {
        UserDTO user = userService.findByEmailToken(principal.getName());
        dto.setUserId(user.getId());
        return customerService.createCartItem(dto);
    }

    // =================
    //   delete EPs   //
    // =================

    @DeleteMapping("/address/{id}")
    @ResponseBody
    public void deleteAddress(Principal principal, @PathVariable Long id) {
        UserDTO user = userService.findByEmailToken(principal.getName());
        customerService.deleteAddress(user.getId(),id);
    }

    @DeleteMapping("/cart-item/{cartItemId}")
    @ResponseBody
    public void deleteCartItem(Principal principal, @PathVariable Long cartItemId) {
        UserEntity user = userService.findUserEntityByEmail(principal.getName());
        customerService.deleteCartItem(user,cartItemId);
    }
}
