package com.schachte.asciiphile.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schachte.asciiphile.model.JwtAuthententicationToken;
import com.schachte.asciiphile.model.User;
import com.schachte.asciiphile.security.util.JwtGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

  private JwtGenerator jwtGenerator = new JwtGenerator();

  private AuthenticationManager authenticationManager;

  public JwtLoginFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException {

    User creds = null;
    try {
      creds = new ObjectMapper().readValue(req.getInputStream(), User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
    GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_ADMIN");
    auths.add(auth);

    return authenticationManager.authenticate(
        new JwtAuthententicationToken(creds.getUsername(), creds.getPassword(), auths));
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication auth)
      throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, auth);
    String token =
        JWT.create()
            .withSubject(((User) auth.getPrincipal()).getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_000))
            .sign(HMAC512("secret".getBytes()));
    response.addHeader("Authorization", "Token " + token);
  }
}
