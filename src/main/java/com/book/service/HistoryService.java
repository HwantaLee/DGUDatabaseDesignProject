package com.book.service;


import com.book.app.dto.RentalRecordResponseDTO;
import com.book.domain.history.History;
import com.book.repository.HistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    // 현재 기한이 지나지 않은 대여 목록 조회
    public List<RentalRecordResponseDTO> getActiveRentals() {
        Date currentDate = new Date();
        List<History> activeRentals = historyRepository.findByDueDateAfterAndIsOverdueFalse(currentDate);

        return activeRentals.stream()
                .map(history -> new RentalRecordResponseDTO(
                        history.getId(),
                        history.getBook().getId(),
                        history.getBook().getTitle(),
                        history.getUser().getUsername(),
                        history.getDueDate(),
                        history.isOverdue(),
                        false // 반납되지 않은 경우만 표시
                ))
                .collect(Collectors.toList());
    }

    // 모든 대여 목록 조회
    public List<RentalRecordResponseDTO> getAllRentals() {
        List<History> allRentals = historyRepository.findAll();

        return allRentals.stream()
                .map(history -> new RentalRecordResponseDTO(
                        history.getId(),
                        history.getBook().getId(),
                        history.getBook().getTitle(),
                        history.getUser().getUsername(),
                        history.getDueDate(),
                        history.isOverdue(),
                        history.getHistoryType() == History.HistoryType.RETURN // 반납 여부 확인
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<History> getDueDateApproachingHistories() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        return historyRepository.findAllByDueDateBetweenAndHistoryType(tomorrow.atStartOfDay(),
                tomorrow.plusDays(1).atStartOfDay(),
                History.HistoryType.RENT);
    }

    @Transactional(readOnly = true)
    public List<History> getOverdueHistories() {
        LocalDate today = LocalDate.now();
        return historyRepository.findAllByDueDateBeforeAndHistoryTypeAndIsOverdue(today.atStartOfDay(),
                History.HistoryType.RENT,
                true);
    }
}