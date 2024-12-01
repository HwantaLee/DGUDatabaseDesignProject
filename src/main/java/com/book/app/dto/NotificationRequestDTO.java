package com.book.app.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDTO {
    private Long notificationId;
    private boolean isRead; // 읽음 여부 업데이트용
}