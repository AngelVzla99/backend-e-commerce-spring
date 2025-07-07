package com.example.springboot.Product.controllers;

import com.example.springboot.Product.converters.ProductInventoryConverter;
import com.example.springboot.Product.dtos.ProductInventoryDTO;
import com.example.springboot.Product.services.ProductInventoryService;
import com.example.springboot.Product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products-inventory")
public class ProductInventoryController {
    @Autowired
    ProductInventoryService productInventoryService;
    @Autowired
    ProductInventoryConverter productInventoryConverter;
    @Autowired
    ProductService productService;

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/{id}")
    @ResponseBody
    public ProductInventoryDTO get(@PathVariable Long id) {
        return productInventoryService.findById(id);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping()
    @ResponseBody
    public Page<ProductInventoryDTO> getAllRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        // request to the database using pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return productInventoryService.findAllPageable(pageable);
    }

    // ===============
    //   post EPs   //
    // ===============

    @PreAuthorize("hasAnyRole('admin')")
    @PostMapping("")
    @ResponseBody
    public ProductInventoryDTO create(@RequestBody ProductInventoryDTO productInventoryDTO) {
        // save new inventory of a product in the db
        return productInventoryService.save(productInventoryDTO);
    }

    // ================
    //   delete EPs  //
    // ================

    @PreAuthorize("hasAnyRole('admin')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        productInventoryService.delete(id);
    }

}
