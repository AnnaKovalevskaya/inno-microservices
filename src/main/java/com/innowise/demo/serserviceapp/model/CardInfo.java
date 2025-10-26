package com.innowise.demo.serserviceapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "card_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String holder;

    @Column(nullable = false)
    private LocalDate expirationDate;
}
