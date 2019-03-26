package com.greenfox.oauth_practice.security;

import com.greenfox.oauth_practice.entity.User;
import com.greenfox.oauth_practice.exception.ResourceNotFoundException;
import com.greenfox.oauth_practice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  @Autowired
  public CustomUserDetailsService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() ->
            new UsernameNotFoundException("User not found with email : " + email)
        );

    return UserPrincipal.create(user);
  }

  @Transactional
  public UserDetails loadUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("User", "id", id)
    );

    return UserPrincipal.create(user);
  }
}
