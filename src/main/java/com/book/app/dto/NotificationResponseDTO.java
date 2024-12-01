package com.book.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class NotificationResponseDTO {
    private Long id;
    private String message;
    private Date createdDate;
    private boolean isRead;
}