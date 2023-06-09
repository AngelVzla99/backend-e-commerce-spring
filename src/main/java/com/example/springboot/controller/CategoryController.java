package com.example.springboot.controller;

import com.example.springboot.converter.CategoryConverter;
import com.example.springboot.dto.CategoryDTO;
import com.example.springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;


    // ===============
    //    get EPs   //
    // ===============
    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping
    @ResponseBody
    public Page<CategoryDTO> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        // request to the database using pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return categoryService.findAllPageable(pageable);
    }

    // ===============
    //   post EPs   //
    // ===============

    @PreAuthorize("hasAnyRole('admin')")
    @PostMapping
    @ResponseBody
    public CategoryDTO save(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.save(categoryDTO);
    }

    // =================
    //   delete EPs   //
    // =================

    @PreAuthorize("hasAnyRole('admin')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
