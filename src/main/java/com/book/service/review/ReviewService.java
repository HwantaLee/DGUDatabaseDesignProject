package com.book.service.review;

import com.book.app.dto.review.ReviewRequestDTO;
import com.book.app.dto.review.ReviewResponseDTO;
import com.book.domain.book.Book;
import com.book.domain.review.Review;
import com.book.domain.user.User;
import com.book.repository.BookRepository;
import com.book.repository.ReviewRepository;
import com.book.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addReview(ReviewRequestDTO reviewRequestDTO) {
        Book book = bookRepository.findById(reviewRequestDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(reviewRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setRating(reviewRequestDTO.getRating());
        review.setContent(reviewRequestDTO.getContent());
        review.setCreatedDate(new Date());
        reviewRepository.save(review);
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewRequestDTO reviewRequestDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRating(reviewRequestDTO.getRating());
        review.setContent(reviewRequestDTO.getContent());
        review.setModifiedDate(new Date());
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setDeleted(true);
        reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> getReviewsByBookId(Long bookId) {
        List<Review> reviews = reviewRepository.findByBookIdAndIsDeletedFalse(bookId);
        return reviews.stream()
                .map(this::toReviewResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public double getAverageRatingByBookId(Long bookId) {
        List<Review> reviews = reviewRepository.findByBookIdAndIsDeletedFalse(bookId);
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    private ReviewResponseDTO toReviewResponseDTO(Review review) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setBookId(review.getBook().getId());
        dto.setUserId(review.getUser().getId());
        dto.setRating(review.getRating());
        dto.setContent(review.getContent());
        dto.setCreatedDate(review.getCreatedDate().toString());
        dto.setModifiedDate(review.getModifiedDate() != null ? review.getModifiedDate().toString() : null);
        return dto;
    }
}
