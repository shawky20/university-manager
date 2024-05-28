package com.university.manager.service;

import com.university.manager.entities.UserDB;
import com.university.manager.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDB saveUser(UserDB userDb){
        userRepo.save(userDb);
        return userDb;
    }

    public Boolean findUserByEmail(String mail){
        UserDB userDB =  userRepo.findByEmail(mail);
        return userDB != null ? true : false;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDB user = userRepo.findByEmail(username);
        System.out.println(user);

        if(user==null) {
            throw new UsernameNotFoundException("User not found with this email"+username);

        }


        System.out.println("Loaded user: " + user.getEmail() + ", Role: " + user.getRole());
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    public boolean resetPassword(String username, String password) {
        // Find the user by their username or email address
        UserDB user = userRepo.findByEmail(username);

        // Check if the user exists
        if (user == null) {
            return false; // User not found
        }

        // Update the user's password with the new password
        user.setPassword(passwordEncoder.encode(password));

        // Save the updated user entity to the database
        userRepo.save(user);

        return true; // Password reset successful
    }
}
