package com.book.domain.history;


import javax.persistence.*;

import com.book.domain.book.Book;
import com.book.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HistoryType historyType;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date dueDate;

    @Column(nullable = false)
    private boolean isOverdue = false;

    public enum HistoryType {
        RENT, RETURN
    }
}
