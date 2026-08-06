package com.lostlink.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Table(name="users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    @JsonIgnore
    private String password;
    private String phone;
    private String role;
//    @OneToMany(mappedBy = "user")
//    private List<LostItem> lostItems;
}
