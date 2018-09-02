package com.schachte.asciiphile.security;

import static com.schachte.asciiphile.security.SecurityConstants.HEADER_STRING;
import static com.schachte.asciiphile.security.SecurityConstants.SECRET;
import static com.schachte.asciiphile.security.SecurityConstants.TOKEN_PREFIX;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private static final Logger LOGGER = Logger.getLogger(JwtAuthorizationFilter.class.getName());

  public JwtAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    String header = req.getHeader(HEADER_STRING);

    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      LOGGER.warning("No authorization token present");
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      LOGGER.info("Checking auth token");
      String user =
          Jwts.parser()
              .setSigningKey(SECRET.getBytes())
              .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
              .getBody()
              .getSubject();

      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, null);
      }
    }
    throw new RuntimeException("Error, user was not found");
  }
}
