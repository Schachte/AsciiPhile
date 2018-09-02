package com.schachte.asciiphile.model;

import java.util.List;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@Data
public class JwtAuthententicationToken extends UsernamePasswordAuthenticationToken {

  private String username;
  private String password;
  private List<GrantedAuthority> grantedAuthorities;

  public JwtAuthententicationToken(
      String username, String password, List<GrantedAuthority> grantedAuthorities) {
    super(username, password);
    this.username = username;
    this.password = password;
    this.grantedAuthorities = grantedAuthorities;
  }
}
