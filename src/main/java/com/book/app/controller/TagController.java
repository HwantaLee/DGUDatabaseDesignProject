package com.book.app.controller;

import com.book.apiPayload.ApiResponse;
import com.book.domain.book.Book;
import com.book.domain.tag.Tag;
import com.book.service.book.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/add/{bookId}")
    public ApiResponse<String> addTagToBook(@PathVariable Long bookId, @RequestBody String tagName) {
        tagService.addTagToBook(bookId, tagName);
        return ApiResponse.onSuccess("Tag added to book successfully");
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<List<Tag>> getTagsByBookId(@PathVariable Long bookId) {
        return ApiResponse.onSuccess(tagService.getTagsByBookId(bookId));
    }

    @GetMapping("/search")
    public ApiResponse<List<Book>> searchBooksByTag(@RequestParam String tagName) {
        return ApiResponse.onSuccess(tagService.searchBooksByTag(tagName));
    }
}