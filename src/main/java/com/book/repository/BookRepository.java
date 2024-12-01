package com.book.repository;


import com.book.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query("SELECT b FROM Book b WHERE (:title IS NULL OR b.title LIKE %:title%) " +
            "AND (:author IS NULL OR b.author LIKE %:author%) " +
            "AND (:status IS NULL OR b.status = :status)")
    List<Book> searchBooks(@Param("title") String title,
                           @Param("author") String author,
                           @Param("status") String status);

    List<Book> findByStatus(Book.Status status); // 특정 상태의 도서 검색
    List<Book> findAllByIsDeletedFalse(); // 삭제되지 않은 모든 도서 조회
    List<Book> findByStatusAndIsDeletedFalse(Book.Status status); // 상태와 삭제 여부로 조회

}
