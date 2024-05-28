package com.university.manager.repositories;

import com.university.manager.entities.Image;
import com.university.manager.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
    List<Image> findByUnit(Unit unit);
}
