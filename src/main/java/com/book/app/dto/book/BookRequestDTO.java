package com.book.app.dto.book;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookRequestDTO {
    private String title;
    private String author;
    private Date releaseDate;
    private int page;
}