package com.book.app.controller;


import com.book.apiPayload.ApiResponse;
import com.book.app.dto.book.*;
import com.book.service.book.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 도서 등록
    @PostMapping("/add")
    public ApiResponse<String> addBook(@RequestBody BookRequestDTO bookRequestDTO) {
        bookService.addBook(bookRequestDTO);
        return ApiResponse.onSuccess("Book added successfully");
    }

    // 도서 수정
    @PutMapping("/update/{id}")
    public ApiResponse<String> updateBook(@PathVariable Long id, @RequestBody BookRequestDTO bookRequestDTO) {
        bookService.updateBook(id, bookRequestDTO);
        return ApiResponse.onSuccess("Book updated successfully");
    }

    // 도서 삭제
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ApiResponse.onSuccess("Book deleted successfully");
    }

    // 도서 상세 조회
    @GetMapping("/bookinfo/{id}")
    public ApiResponse<BookResponseDTO> getBookDetails(@PathVariable Long id) {
        return ApiResponse.onSuccess(bookService.getBookDetails(id));
    }

    // 도서 검색
    @PostMapping("/search")
    public ApiResponse<List<BookResponseDTO>> searchBooks(@RequestBody BookSearchDTO searchDTO) {
        return ApiResponse.onSuccess(bookService.searchBooks(searchDTO));
    }

    // 도서 목록 조회
    @GetMapping("/list")
    public ApiResponse<List<BookListResponseDTO>> getBooks(@RequestParam(required = false) String status) {
        return ApiResponse.onSuccess(bookService.getBooksByStatus(status));
    }

    // 도서 대출
    @PostMapping("/borrow")
    public ApiResponse<String> borrowBook(@RequestBody BorrowRequestDTO borrowRequestDTO) {
        bookService.borrowBook(borrowRequestDTO);
        return ApiResponse.onSuccess("Book borrowed successfully");
    }

    // 도서 반납
    @PostMapping("/return")
    public ApiResponse<String> returnBook(@RequestBody ReturnRequestDTO returnRequestDTO) {
        bookService.returnBook(returnRequestDTO);
        return ApiResponse.onSuccess("Book returned successfully");
    }


}