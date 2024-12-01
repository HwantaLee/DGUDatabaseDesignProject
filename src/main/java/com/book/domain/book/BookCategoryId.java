package com.book.domain.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class BookCategoryId implements Serializable {

    private Long book; // Book의 ID
    private Long category; // Category의 ID

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCategoryId that = (BookCategoryId) o;
        return Objects.equals(book, that.book) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, category);
    }
}