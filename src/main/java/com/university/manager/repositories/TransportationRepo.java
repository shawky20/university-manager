package com.university.manager.repositories;

import com.university.manager.entities.Transportation;
import com.university.manager.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportationRepo extends JpaRepository<Transportation,Long> {
    List<Transportation> findByUnit(Unit unit);
    void deleteByUnit(Unit unit);
}
