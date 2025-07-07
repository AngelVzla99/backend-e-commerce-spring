package com.example.springboot.Product.controllers;

import com.example.springboot.Product.converters.ProductConverter;
import com.example.springboot.Product.dtos.ProductDTO;
import com.example.springboot.Product.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// TODO: The categories have to be leafs in the tree of categories

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductConverter productConverter;

    // ===============
    //    get EPs   //
    // ===============

    @GetMapping("/paginated-search")
    @ResponseBody
    public Page<ProductDTO> paginatedSearch(
            @RequestParam String queryText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ){
        // request to the database using pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by( sortDirection, sortBy));
        return productService.paginatedSearch(queryText,pageable);
    }

    @GetMapping("/most-popular")
    @ResponseBody
    public Page<ProductDTO> mostPopular(){
        int page = 0, size = 10;
        String sortBy = "number_of_purchase";
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return productService.mostPopulars(pageable);
    }

    @GetMapping("/best-sales")
    @ResponseBody
    public Page<ProductDTO> bestSales(){
        int size = 10;
        long qty = productService.count()/size;
        int idx = (int)(Math.random() * qty);
        Pageable pageable = PageRequest.of(idx, size);
        return productService.findAllPageable(pageable);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProductDTO get(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping()
    @ResponseBody
    public Page<ProductDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        // request to the database using pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by( sortDirection, sortBy));
        return productService.findAllPageable(pageable);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProducts() {
        return ResponseEntity.ok( productService.countProducts() );
    }

    // ===============
    //   post EPs   //
    // ===============

    @PreAuthorize("hasAnyRole('admin')")
    @PostMapping("/create")
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.save(productDTO),HttpStatus.CREATED);
    }

    // ================
    //   delete EPs  //
    // ================

    @PreAuthorize("hasAnyRole('admin')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
