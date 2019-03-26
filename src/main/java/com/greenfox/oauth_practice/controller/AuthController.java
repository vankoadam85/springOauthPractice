package com.greenfox.oauth_practice.controller;

import com.greenfox.oauth_practice.entity.AuthProvider;
import com.greenfox.oauth_practice.entity.User;
import com.greenfox.oauth_practice.exception.BadRequestException;
import com.greenfox.oauth_practice.payload.ApiResponse;
import com.greenfox.oauth_practice.payload.AuthResponse;
import com.greenfox.oauth_practice.payload.LoginRequest;
import com.greenfox.oauth_practice.payload.SignUpRequest;
import com.greenfox.oauth_practice.repository.UserRepository;
import com.greenfox.oauth_practice.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private AuthenticationManager authenticationManager;
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private TokenProvider tokenProvider;

  @Autowired
  public AuthController(
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      TokenProvider tokenProvider){
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenProvider = tokenProvider;
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(),
            loginRequest.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = tokenProvider.createToken(authentication);
    return ResponseEntity.ok(new AuthResponse(token));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    if(userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new BadRequestException("Email address already in use.");
    }

    // Creating user's account
    User user = new User();
    user.setName(signUpRequest.getName());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(signUpRequest.getPassword());
    user.setProvider(AuthProvider.local);

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User result = userRepository.save(user);

    URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath().path("/user/me")
        .buildAndExpand(result.getId()).toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "User registered successfully@"));
  }

}
