package com.book.app.controller;


import com.book.apiPayload.ApiResponse;
import com.book.app.dto.RentalRecordResponseDTO;
import com.book.service.HistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    // 현재 기한이 지나지 않은 대여 목록 조회
    @GetMapping("/active-rentals")
    public ApiResponse<List<RentalRecordResponseDTO>> getActiveRentals() {
        return ApiResponse.onSuccess(historyService.getActiveRentals());
    }

    // 모든 대여 목록 조회
    @GetMapping("/all-rentals")
    public ApiResponse<List<RentalRecordResponseDTO>> getAllRentals() {
        return ApiResponse.onSuccess(historyService.getAllRentals());
    }
}