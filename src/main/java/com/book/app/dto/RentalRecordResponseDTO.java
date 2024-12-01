package com.book.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class RentalRecordResponseDTO {
    private Long historyId;
    private Long bookId;
    private String bookTitle;
    private String username;
    private Date dueDate;
    private boolean isOverdue;
    private boolean isReturned;
}