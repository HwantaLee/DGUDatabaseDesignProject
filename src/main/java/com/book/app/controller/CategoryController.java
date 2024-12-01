package com.book.app.controller;

import com.book.apiPayload.ApiResponse;
import com.book.domain.book.Category;
import com.book.service.book.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ApiResponse<String> addCategory(@RequestBody String name) {
        categoryService.addCategory(name);
        return ApiResponse.onSuccess("Category added successfully");
    }

    @GetMapping("/all")
    public ApiResponse<List<Category>> getAllCategories() {
        return ApiResponse.onSuccess(categoryService.getAllCategories());
    }

    @PostMapping("/add-to-book")
    public ApiResponse<String> addCategoryToBook(@RequestParam Long bookId, @RequestParam Long categoryId) {
        categoryService.addCategoryToBook(bookId, categoryId);
        return ApiResponse.onSuccess("Category successfully added to book");
    }
}