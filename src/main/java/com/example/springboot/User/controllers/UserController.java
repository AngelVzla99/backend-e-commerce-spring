package com.example.springboot.User.controllers;

import com.example.springboot.User.dto.PartialUpdateUserDto;
import com.example.springboot.User.dto.UserRequestAddRole;
import com.example.springboot.User.dto.UserDTO;
import com.example.springboot.User.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    // ===============
    //    get EPs   //
    // ===============

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/{id}")
    @ResponseBody
    public UserDTO getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping()
    @ResponseBody
    public Page<UserDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        System.out.println("Controller");
        // request to the database using pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by( sortDirection, sortBy));
        return userService.findAllPageable(pageable);
    }

    // ===============
    //   post EPs   //
    // ===============

    @PreAuthorize("hasAnyRole('admin')")
    @PostMapping("/create-user")
    @ResponseBody
    public UserDTO createUserAdmin( @Valid @RequestBody UserDTO user) {
        if( userService.isEmailInDataBase(user.getEmail()) )
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The email is already in the database");
        return userService.create(user);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @PutMapping("/{userId}/add-roles")
    @ResponseBody
    public UserDTO addRole(@PathVariable Long userId, @RequestBody UserRequestAddRole request) {
        return userService.addRole( userId, request.getRoleIds() );
    }

    // ================
    //   delete EPs  //
    // ================

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    // ================
    //   delete EPs  //
    // ================

    @PreAuthorize("hasAnyRole('admin')")
    @PatchMapping("/{id}")
    @ResponseBody
    public UserDTO updateUserByAdmin(@Valid @RequestBody PartialUpdateUserDto dto, @PathVariable Long id) {
        return userService.partialUpdate(id, dto);
    }
}
