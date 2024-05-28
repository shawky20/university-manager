package com.university.manager.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "Transportation")
@Data
public class Transportation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transportation_id")
    private Long id;
    private String Address;
    private String date;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;


}
