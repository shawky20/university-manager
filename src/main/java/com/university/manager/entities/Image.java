package com.university.manager.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Image")
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;
}