package com.book.repository;

import com.book.domain.Notification;
import com.book.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserOrderByCreatedDateDesc(User user); // 특정 사용자 알림 조회
}