package com.integraobra.integraApi.model;

import com.integraobra.integraApi.utils.ReputationClient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Table(name = "clients")
@Getter @Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private ReputationClient reputation;
    private String frontPhotoIne;
    private String backPhotoIne;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Client(String name, String email, String phone, ReputationClient reputation, String frontPhotoIne, String backPhotoIne) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.reputation = reputation;
        this.frontPhotoIne = frontPhotoIne;
        this.backPhotoIne = backPhotoIne;
    }

}
