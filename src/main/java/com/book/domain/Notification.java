package com.book.domain;

import com.book.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdDate;

    @Column(nullable = false)
    private boolean isRead = false; // 읽음 여부
}