package com.book.scheduler;

import com.book.domain.history.History;
import com.book.repository.HistoryRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class OverdueScheduler {

    private final HistoryRepository historyRepository;

    public OverdueScheduler(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }


    @Scheduled(cron = "0 0 0/3 * * ?") // 매일 자정부터 3시간마다 실행
    public void checkAndMarkOverdue() {
        Date currentDate = new Date();

        // 연체 상태를 확인해야 하는 기록 검색
        List<History> overdueHistories = historyRepository.findByDueDateBeforeAndIsOverdueFalse(currentDate);

        // 연체 상태로 업데이트
        for (History history : overdueHistories) {
            history.setOverdue(true);
        }

        // 변경 사항 저장
        historyRepository.saveAll(overdueHistories);
    }
}