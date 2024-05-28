package com.university.manager.repositories;

import com.university.manager.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepo extends JpaRepository<Unit,Long> {
    List<Unit> findByUniversity(String universityName);
}
