package com.schachte.asciiphile.model;

import java.util.Collection;
import javax.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
public class User implements UserDetails {

  public User() {}

  public User(String username, String pass, String role) {
    this.username = username;
    this.password = pass;
    this.role = role;
  }

  public User(User username) {
    this.email = username.getEmail();
    this.password = username.getPassword();
    this.premium = username.getPremium();
    this.storedBytes = username.getStoredBytes();
    this.username = username.getUsername();
  }

  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "premium")
  private int premium;

  @Column(name = "storedbytes")
  private int storedBytes;

  @Column(name = "role")
  private String role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
