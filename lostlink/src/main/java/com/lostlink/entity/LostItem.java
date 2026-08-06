package com.lostlink.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
@Table(name="lost_items")
public class LostItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private String itemName;
    private String category;
    @Column(length=1000)
    private String description;
    private String locationLost;
    private LocalDate dateLost;
    private String imageUrl;
    private String status;


}
