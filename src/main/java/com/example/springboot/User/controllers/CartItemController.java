package com.example.springboot.User.controllers;

import com.example.springboot.User.converter.CartItemConverter;
import com.example.springboot.User.dto.CartItemDTO;
import com.example.springboot.User.dto.UserCartUpdate;
import com.example.springboot.User.services.CartItemService;
import com.example.springboot.User.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users-cart")
public class CartItemController {
    @Autowired
    UserService userService;
    @Autowired
    CartItemConverter cartItemConverter;
    @Autowired
    CartItemService cartItemService;

    // ===============
    //    get EPs   //
    // ===============

    @GetMapping()
    @ResponseBody
    public List<CartItemDTO> getCart(Principal principal) {
        return userService.getCartItems(principal.getName());
    }

    // ===============
    //   post EPs   //
    // ===============

    @PutMapping("/update")
    @ResponseBody
    public void updateUserCart(
            Principal principal,
            @RequestBody UserCartUpdate request
    ) {
        userService.updateUserCart( principal.getName(), request.getProducts() );
    }
}
