package com.book.service.book;


import com.book.app.dto.book.*;
import com.book.domain.book.Book;
import com.book.domain.book.Book.Status;
import com.book.domain.history.History;
import com.book.domain.user.User;
import com.book.repository.BookRepository;
import com.book.repository.HistoryRepository;
import com.book.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    public BookService(BookRepository bookRepository, HistoryRepository historyRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
    }
    // 도서 등록
    public void addBook(BookRequestDTO bookRequestDTO) {
        Book book = new Book();
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setReleaseDate(bookRequestDTO.getReleaseDate());
        book.setPageNumber(bookRequestDTO.getPage());
        book.setStatus(Status.AVAILABLE);
        bookRepository.save(book);
    }

    // 도서 수정
    public void updateBook(Long id, BookRequestDTO bookRequestDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setReleaseDate(bookRequestDTO.getReleaseDate());
        book.setPageNumber(bookRequestDTO.getPage());
        bookRepository.save(book);
    }

    // 도서 삭제 (비활성화 처리)
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setDeleted(true);
        bookRepository.save(book);
    }

    // 도서 상세 조회
    public BookResponseDTO getBookDetails(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return toBookResponseDTO(book);
    }

    // 도서 검색 및 목록 조회
    public List<BookResponseDTO> searchBooks(BookSearchDTO searchDTO) {
        List<Book> books = bookRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 동적 조건 추가
            if (searchDTO.getTitle() != null && !searchDTO.getTitle().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + searchDTO.getTitle() + "%"));
            }
            if (searchDTO.getAuthor() != null && !searchDTO.getAuthor().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("author"), "%" + searchDTO.getAuthor() + "%"));
            }
            if (searchDTO.getCategory() != null && !searchDTO.getCategory().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category"), searchDTO.getCategory()));
            }
            if (searchDTO.getTag() != null && !searchDTO.getTag().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.join("tags").get("name"), "%" + searchDTO.getTag() + "%"));
            }
            if (searchDTO.getStatus() != null && !searchDTO.getStatus().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), searchDTO.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        // 검색 결과를 BookResponseDTO로 변환
        return books.stream()
                .map(book -> new BookResponseDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getStatus().toString()))
                .collect(Collectors.toList());
    }

    // Helper: Book -> BookResponseDTO 변환
    private BookResponseDTO toBookResponseDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setReleaseDate(book.getReleaseDate());
        dto.setPage(book.getPageNumber());
        dto.setStatus(book.getStatus().name());
        return dto;
    }
    // 도서 목록 조회
    @Transactional(readOnly = true)
    public List<BookListResponseDTO> getBooksByStatus(String status) {
        List<Book> books;

        // 상태값에 따른 조회
        if (status == null || status.isEmpty()) {
            books = bookRepository.findAllByIsDeletedFalse();
        } else {
            books = bookRepository.findByStatusAndIsDeletedFalse(Book.Status.valueOf(status.toUpperCase()));
        }

        // DTO로 변환하여 반환
        return books.stream()
                .map(book -> {
                    BookListResponseDTO dto = new BookListResponseDTO();
                    dto.setId(book.getId());
                    dto.setTitle(book.getTitle());
                    dto.setAuthor(book.getAuthor());
                    dto.setStatus(book.getStatus().toString());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    // 대출 요청
    @Transactional
    public void borrowBook(BorrowRequestDTO borrowRequestDTO) {
        Book book = bookRepository.findById(borrowRequestDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(borrowRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (book.getStatus() != Book.Status.AVAILABLE) {
            throw new RuntimeException("Book is not available for borrowing");
        }

        // 대출 기록 생성
        History history = new History();
        history.setBook(book);
        history.setUser(user);
        history.setCreateDate(new Date());

        // 대출 마감일 설정 (기본은 7일로 함)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        history.setDueDate(calendar.getTime());

        history.setHistoryType(History.HistoryType.RENT);
        historyRepository.save(history);


        // 책의 상태 및 대출자 설정
        book.setStatus(Book.Status.BORROWED);
        book.setRender(user); // 대출자 설정
        bookRepository.save(book);
    }

    // 반납 요청
    @Transactional
    public void returnBook(ReturnRequestDTO returnRequestDTO) {
        Book book = bookRepository.findById(returnRequestDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getStatus() != Book.Status.BORROWED) {
            throw new RuntimeException("Book is not currently borrowed");
        }

        User user = userRepository.findById(returnRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!book.getRender().getId().equals(user.getId())) {
            throw new RuntimeException("This user did not borrow the book");
        }

        book.setStatus(Book.Status.AVAILABLE);
        book.setRender(null); // 대출자 해제
        bookRepository.save(book);

        // 반납 이력 생성
        History history = new History();
        history.setBook(book);
        history.setUser(user);
        history.setHistoryType(History.HistoryType.RETURN);
        history.setCreateDate(new Date());
        historyRepository.save(history);
    }
}