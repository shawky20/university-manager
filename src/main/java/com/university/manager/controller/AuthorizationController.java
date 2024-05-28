package com.university.manager.controller;


import com.university.manager.dtos.AuthResponse;
import com.university.manager.dtos.User;
import com.university.manager.entities.UserDB;
import com.university.manager.security.JwtProvider;
import com.university.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)  {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String mobile = user.getMobile();
        String role = user.getRole();


        Boolean isEmailExist = userService.findUserByEmail(email);
        if (isEmailExist) {
            //throw new Exception("Email Is Already Used With Another Account");
            throw new BadCredentialsException("Email Is Already Used With Another Account");
        }

        UserDB createdUser = new UserDB();
        createdUser.setEmail(email);
        createdUser.setName(fullName);
        createdUser.setPhone(mobile);
        createdUser.setRole(role);
        createdUser.setPassword(passwordEncoder.encode(password));

        userService.saveUser(createdUser);



        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);


        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody User loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }




    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        if(userDetails == null) {
            throw new BadCredentialsException("Invalid username and password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader String accessToken) {
        // todo: add validation here

        // Invalidate the token (add it to the blacklist)
        JwtProvider.invalidateToken(accessToken);

        // todo: invalidate user session in front

        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<AuthResponse> forgetPassword(@RequestBody String email) {
        // Validate the email
        Boolean isEmailExist = userService.findUserByEmail(email);
        if (!isEmailExist) {
           throw new BadCredentialsException("no mail exists");
        }

        // Send the reset token to the user via email (implement this)
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Email Exists");
        authResponse.setStatus(true);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody User loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();


        // Reset the password
        boolean passwordResetSuccessful = userService.resetPassword(username, password);

        if (passwordResetSuccessful) {
            return new ResponseEntity<>("Password reset successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to reset password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
