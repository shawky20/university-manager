package com.university.manager.entities;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class UserDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String phone;
    private String address;
    private String govId;
}

