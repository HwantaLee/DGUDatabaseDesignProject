package com.book.service;

import com.book.app.dto.NotificationResponseDTO;
import com.book.domain.history.History;
import com.book.domain.Notification;
import com.book.domain.user.User;
import com.book.repository.NotificationRepository;
import com.book.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // 알림 생성
    @Transactional
    public void createNotification(User user, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setCreatedDate(new Date());
        notificationRepository.save(notification);
    }

    // 반납 마감 알림 생성
    @Transactional
    public void createDueDateNotification(History history) {
        String message = "The book \"" + history.getBook().getTitle() + "\" is due for return on " + history.getDueDate();
        createNotification(history.getUser(), message);
    }

    // 연체 알림 생성
    @Transactional
    public void createOverdueNotification(History history) {
        String message = "The book \"" + history.getBook().getTitle() + "\" is overdue!";
        createNotification(history.getUser(), message);
    }

    // 사용자 알림 조회
    @Transactional
    public List<NotificationResponseDTO> getNotificationsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByUserOrderByCreatedDateDesc(user).stream()
                .map(notification -> new NotificationResponseDTO(
                        notification.getId(),
                        notification.getMessage(),
                        notification.getCreatedDate(),
                        notification.isRead()
                ))
                .collect(Collectors.toList());
    }

    // 알림 읽음 상태 업데이트
    @Transactional
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}