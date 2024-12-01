package com.book.app.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchDTO {
    private String title;
    private String author;
    private String category;
    private String tag;
    private String status; // AVAILABLE, BORROWED, LOST
}