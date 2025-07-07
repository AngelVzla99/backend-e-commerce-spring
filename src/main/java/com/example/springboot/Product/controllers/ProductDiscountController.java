package com.example.springboot.Product.controllers;

import com.example.springboot.Product.converters.ProductDiscountConverter;
import com.example.springboot.Product.dtos.ProductDiscountDTO;
import com.example.springboot.Product.services.ProductDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-discount")
public class ProductDiscountController {
    @Autowired
    ProductDiscountService productDiscountService;
    @Autowired
    ProductDiscountConverter productDiscountConverter;

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/{id}")
    @ResponseBody
    public ProductDiscountDTO get(@PathVariable Long id) {
        return productDiscountService.findById(id);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping()
    @ResponseBody
    public Page<ProductDiscountDTO> getAllRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        // request to the database using pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return productDiscountService.findAllPageable(pageable);
    }

    // ===============
    //   post EPs   //
    // ===============

    @PreAuthorize("hasAnyRole('admin')")
    @PostMapping()
    @ResponseBody
    public ProductDiscountDTO create(@RequestBody ProductDiscountDTO productDiscountDTO) {
        return productDiscountService.save(productDiscountDTO);
    }

    // ================
    //   delete EPs  //
    // ================

    @PreAuthorize("hasAnyRole('admin')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        productDiscountService.delete(id);
    }
}
