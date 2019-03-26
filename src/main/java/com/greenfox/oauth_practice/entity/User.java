package com.greenfox.oauth_practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String name;

  @Email
  @Column(nullable = false)
  private String email;

  @JsonIgnore
  private String password;

  @NotNull
  @Enumerated(EnumType.STRING)
  private AuthProvider provider;

  private String providerId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public AuthProvider getProvider() {
    return provider;
  }

  public void setProvider(AuthProvider provider) {
    this.provider = provider;
  }

  public String getProviderId() {
    return providerId;
  }

  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }
}
