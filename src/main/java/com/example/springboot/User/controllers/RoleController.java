package com.example.springboot.User.controllers;

import com.example.springboot.User.dto.RoleDTO;
import com.example.springboot.User.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    RoleService roleService;

    // ===============
    //    get EPs   //
    // ===============

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/{id}")
    @ResponseBody
    public RoleDTO get(@PathVariable Long id){
        return roleService.findById(id);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/all")
    @ResponseBody
    public List<RoleDTO> getAll(){
        return roleService.findAll();
    }

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("")
    @ResponseBody
    public Page<RoleDTO> getAllRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        // request to the database using pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return roleService.findAllPageable(pageable);
    }

    // ===============
    //   post EPs   //
    // ===============

    @PreAuthorize("hasAnyRole('admin')")
    @PostMapping("/create")
    @ResponseBody
    public RoleDTO create(@RequestBody RoleDTO roleDTO) {
        return roleService.save(roleDTO);
    }

    // ================
    //   delete EPs  //
    // ================

    @PreAuthorize("hasAnyRole('admin')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id){
        roleService.delete(id);
    }

}
