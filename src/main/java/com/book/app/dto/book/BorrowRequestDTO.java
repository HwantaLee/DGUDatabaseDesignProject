package com.book.app.dto.book;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowRequestDTO {
    private Long bookId;
    private Long userId; // 대출자의 ID
}