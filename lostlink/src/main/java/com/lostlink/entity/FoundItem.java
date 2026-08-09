package com.lostlink.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="found_items")
public class FoundItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String itemName;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @Column(length = 1000)
    private String description;
    private LocalDate dateFound;
    private String locationFound;
    private String status;
    private String imageUrl;
}
