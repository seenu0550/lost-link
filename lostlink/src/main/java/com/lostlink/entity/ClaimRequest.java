package com.lostlink.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="claim_requests")
public class ClaimRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="lost_item_id")
    private LostItem lostItem;
    @ManyToOne
    @JoinColumn(name = "found_item_id")
    private FoundItem foundItem;
    private String message;
    private String proof;
    private String status;
    private LocalDate requestDate;

}
