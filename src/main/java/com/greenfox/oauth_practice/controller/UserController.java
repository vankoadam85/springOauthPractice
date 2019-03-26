package com.greenfox.oauth_practice.controller;

import com.greenfox.oauth_practice.entity.User;
import com.greenfox.oauth_practice.exception.ResourceNotFoundException;
import com.greenfox.oauth_practice.repository.UserRepository;
import com.greenfox.oauth_practice.security.CurrentUser;
import com.greenfox.oauth_practice.security.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private UserRepository userRepository;

  public UserController(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @GetMapping("/user/me")
  @PreAuthorize("hasRole('USER')")
  public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    return userRepository.findById(userPrincipal.getId())
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
  }
}
