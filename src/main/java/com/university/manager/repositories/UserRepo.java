package com.university.manager.repositories;


import com.university.manager.entities.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserDB,Long> {


    UserDB findByEmail(String username);
}
