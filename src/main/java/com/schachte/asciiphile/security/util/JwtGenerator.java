package com.schachte.asciiphile.security.util;

import com.schachte.asciiphile.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {
  public String generate(User user) {
    Claims claims = Jwts.claims().setSubject(user.getUsername());

    claims.put("password", user.getPassword());

    return Jwts.builder()
        .setClaims(claims)
        // TODO: Load this from env var
        .signWith(SignatureAlgorithm.HS512, "secret")
        .compact();
  }
}
