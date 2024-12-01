package com.book.domain.book;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(BookCategoryId.class) // 복합 키 지정
public class BookCategory {

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Id
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}