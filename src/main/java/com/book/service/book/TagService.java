package com.book.service.book;

import com.book.domain.book.Book;
import com.book.domain.tag.Tag;
import com.book.repository.BookRepository;
import com.book.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;
    private final BookRepository bookRepository;

    public TagService(TagRepository tagRepository, BookRepository bookRepository) {
        this.tagRepository = tagRepository;
        this.bookRepository = bookRepository;
    }

    public void addTagToBook(Long bookId, String tagName) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));

        Tag tag = tagRepository.findByName(tagName)
                .orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    return tagRepository.save(newTag);
                });

        book.getTags().add(tag);
        bookRepository.save(book);
    }

    public List<Tag> getTagsByBookId(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));

        return List.copyOf(book.getTags());
    }

    public List<Book> searchBooksByTag(String tagName) {
        Optional<Tag> tag = tagRepository.findByName(tagName);
        if (tag.isEmpty()) {
            throw new IllegalArgumentException("No books found with tag: " + tagName);
        }
        return List.copyOf(tag.get().getBooks());
    }
}
