package com.book.app.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDTO {
    private Long id;
    private Long bookId;
    private Long userId;
    private int rating;
    private String content;
    private String createdDate;
    private String modifiedDate;
}