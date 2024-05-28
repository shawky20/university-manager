package com.university.manager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.university.manager.constants.University;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Unit")
@Data
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long id;

    private String details;
    private String price;
    private String Address;
    private String university; // the unit near this university
    private String region;

    @JsonIgnore
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transportation> transportations;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

}
