package com.book.service.book;

import com.book.domain.book.Book;
import com.book.domain.book.BookCategory;
import com.book.domain.book.Category;
import com.book.repository.BookCategoryRepository;
import com.book.repository.BookRepository;
import com.book.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;

    public CategoryService(CategoryRepository categoryRepository, BookRepository bookRepository, BookCategoryRepository bookCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.bookCategoryRepository = bookCategoryRepository;
    }

    public void addCategory(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new IllegalArgumentException("Category already exists: " + name);
        }
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }

    @Transactional
    public void addCategoryToBook(Long bookId, Long categoryId) {
        // 도서 조회
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // 카테고리 조회
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 기존에 이미 매핑되어 있는지 확인
        boolean exists = bookCategoryRepository.existsByBookAndCategory(book, category);
        if (exists) {
            throw new RuntimeException("The category is already associated with this book");
        }

        // 새로운 BookCategory 엔티티 생성
        BookCategory bookCategory = new BookCategory();
        bookCategory.setBook(book);
        bookCategory.setCategory(category);

        // 저장
        bookCategoryRepository.save(bookCategory);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
