package com.book.domain.user;

import javax.persistence.*;

import com.book.app.dto.JoinDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role {
        ADMIN, USER
    }

    public static User toUser(JoinDTO joinDTO) {
        User user = new User();
        user.setUsername(joinDTO.getUsername());
        user.setPassword(joinDTO.getPassword()); // 비밀번호는 암호화 필요
        user.setName(joinDTO.getName());
        user.setRole(Role.USER); // 기본적으로 일반 사용자로 설정
        return user;
    }
}

