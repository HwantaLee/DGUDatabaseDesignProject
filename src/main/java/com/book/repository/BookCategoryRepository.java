package com.book.repository;

import com.book.domain.book.Book;
import com.book.domain.book.BookCategory;
import com.book.domain.book.BookCategoryId;
import com.book.domain.book.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategoryId> {
    boolean existsByBookAndCategory(Book book, Category category);
}
