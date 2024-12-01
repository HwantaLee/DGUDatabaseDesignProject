package com.book.app.controller;

import com.book.apiPayload.ApiResponse;
import com.book.app.dto.review.ReviewRequestDTO;
import com.book.app.dto.review.ReviewResponseDTO;
import com.book.service.review.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ApiResponse<String> addReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        reviewService.addReview(reviewRequestDTO);
        return ApiResponse.onSuccess("Review added successfully");
    }

    @PutMapping("/update/{id}")
    public ApiResponse<String> updateReview(@PathVariable Long id, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        reviewService.updateReview(id, reviewRequestDTO);
        return ApiResponse.onSuccess("Review updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ApiResponse.onSuccess("Review deleted successfully");
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<List<ReviewResponseDTO>> getReviewsByBookId(@PathVariable Long bookId) {
        return ApiResponse.onSuccess(reviewService.getReviewsByBookId(bookId));
    }

    @GetMapping("/book/{bookId}/average-rating")
    public ApiResponse<Double> getAverageRating(@PathVariable Long bookId) {
        return ApiResponse.onSuccess(reviewService.getAverageRatingByBookId(bookId));
    }
}
