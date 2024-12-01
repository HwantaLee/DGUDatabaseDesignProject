package com.book.repository;

import com.book.domain.history.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUserId(Long userId); // 특정 사용자의 대출 이력 조회
    List<History> findByDueDateBeforeAndIsOverdueFalse(Date currentDate); // 사용자 연체여부를 scheduler통해 관리하기 위한 메서드
    List<History> findByDueDateAfterAndIsOverdueFalse(Date currentDate); // 기한이 지나지 않은 대여 목록
    List<History> findAll(); // 모든 대여 목록
    List<History> findAllByDueDateBetweenAndHistoryType(LocalDateTime startDate, LocalDateTime endDate, History.HistoryType historyType); // 반환 기한이 특정 기간에 포함되는 대여 기록 조회
    List<History> findAllByDueDateBeforeAndHistoryTypeAndIsOverdue(LocalDateTime dueDate, History.HistoryType historyType, boolean isOverdue); // 기한이 지난 연체 기록 조회


}