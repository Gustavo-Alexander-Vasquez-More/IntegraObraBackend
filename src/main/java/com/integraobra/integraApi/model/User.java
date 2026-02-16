package com.integraobra.integraApi.model;

import com.integraobra.integraApi.utils.RoleUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Table(name = "users")
@Getter @Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private RoleUser role;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    //Constructor para crear usuarios
    public User(String username, String password, RoleUser role) {
        this.role = role;
        this.username = username;
        this.password = password;
    }

}
