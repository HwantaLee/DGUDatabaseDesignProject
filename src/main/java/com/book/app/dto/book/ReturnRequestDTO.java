package com.book.app.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnRequestDTO {
    private Long bookId;
    private Long userId; // 반납자의 ID
}