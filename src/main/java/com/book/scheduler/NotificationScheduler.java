package com.book.scheduler;

import com.book.domain.history.History;
import com.book.service.HistoryService;
import com.book.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class NotificationScheduler {

    private final HistoryService historyService;
    private final NotificationService notificationService;

    public NotificationScheduler(HistoryService historyService, NotificationService notificationService) {
        this.historyService = historyService;
        this.notificationService = notificationService;
    }

    // 반납 마감일 하루 전 알림
    @Scheduled(cron = "0 0 9 * * ?") // 매일 오전 9시 실행
    public void sendDueDateNotifications() {
        List<History> histories = historyService.getDueDateApproachingHistories(); // 기한 하루 전 대여 기록 조회
        for (History history : histories) {
            notificationService.createDueDateNotification(history);
        }
    }

    // 연체 발생 알림
    @Scheduled(cron = "0 0 9 * * ?") // 매일 오전 9시 실행
    public void sendOverdueNotifications() {
        List<History> overdueHistories = historyService.getOverdueHistories(); // 연체된 대여 기록 조회
        for (History history : overdueHistories) {
            notificationService.createOverdueNotification(history);
        }
    }
}