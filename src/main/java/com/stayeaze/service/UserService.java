package com.stayeaze.service;

import com.stayeaze.model.Users;
import com.stayeaze.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
  @Autowired
  AuthenticationManager authManager;
  @Autowired
  private JWTService jwtService;
  @Autowired
  private UsersRepo repo;

  public Users register(Users user) {
    user.setPassword(encoder.encode(user.getPassword()));
    repo.save(user);
    return user;
  }

  public String verify(Users user) {
    Authentication authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    if (authentication.isAuthenticated()) {
      return jwtService.generateToken(user.getUsername());
    } else {
      return "fail";
    }
  }
}