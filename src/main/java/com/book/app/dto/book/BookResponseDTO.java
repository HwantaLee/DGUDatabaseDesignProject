package com.book.app.dto.book;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookResponseDTO {
    private Long id;
    private String title;
    private String author;
    private Date releaseDate;
    private int page;
    private String status; // AVAILABLE, BORROWED, LOST
    // 4개의 인자를 받는 생성자 추가
    public BookResponseDTO(Long id, String title, String author, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
    }

    // 기본 생성자 (필수: NoArgsConstructor를 위해 유지)
    public BookResponseDTO() {}
}