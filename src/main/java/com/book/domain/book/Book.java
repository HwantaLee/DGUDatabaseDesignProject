package com.book.domain.book;

import javax.persistence.*;
import com.book.domain.user.User;
import com.book.domain.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(nullable = false, length = 60)
    private String author;

    @ManyToOne
    @JoinColumn(name = "render_id")
    private User render; // 대출자

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date releaseDate;

    @Column(nullable = false)
    private int pageNumber;

    @Column(nullable = false)
    private int page;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public enum Status {
        AVAILABLE, BORROWED, LOST
    }

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookCategory> bookCategories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "book_tags",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}