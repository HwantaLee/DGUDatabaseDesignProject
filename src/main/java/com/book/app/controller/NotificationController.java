package com.book.app.controller;

import com.book.apiPayload.ApiResponse;
import com.book.app.dto.NotificationResponseDTO;
import com.book.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // 사용자 알림 조회
    @GetMapping("/user/{userId}")
    public ApiResponse<List<NotificationResponseDTO>> getUserNotifications(@PathVariable Long userId) {
        return ApiResponse.onSuccess(notificationService.getNotificationsByUser(userId));
    }

    // 알림 읽음 처리
    @PostMapping("/read/{notificationId}")
    public ApiResponse<String> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ApiResponse.onSuccess("Notification marked as read");
    }
}