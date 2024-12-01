package com.book.app.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookListResponseDTO {
    private Long id;
    private String title;
    private String author;
    private String status; // AVAILABLE, BORROWED, LOST
}