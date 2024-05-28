package com.university.manager.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String date;
    private String cost;
    private int deals;

    @ManyToOne
    @JoinColumn(name = "user_id") // means every book has one owner
    private UserDB user;

    @OneToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

}