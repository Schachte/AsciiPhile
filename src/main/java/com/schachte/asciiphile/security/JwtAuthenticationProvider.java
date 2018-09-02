package com.schachte.asciiphile.security;

import com.schachte.asciiphile.model.JwtAuthententicationToken;
import com.schachte.asciiphile.security.util.JwtValidator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  @Autowired private JwtValidator validator;

  @Qualifier("userDetailsServiceImpl")
  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected UserDetails retrieveUser(
      String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
      throws AuthenticationException {
    JwtAuthententicationToken jwtToken =
        (JwtAuthententicationToken) usernamePasswordAuthenticationToken;

    UserDetails jwtUser = userDetailsService.loadUserByUsername(jwtToken.getUsername());

    List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
    GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_ADMIN");
    auths.add(auth);

    return jwtUser;
  }

  @Override
  public boolean supports(Class<?> inputClass) {
    return JwtAuthententicationToken.class.isAssignableFrom(inputClass);
  }

  @Override
  protected void additionalAuthenticationChecks(
      UserDetails userDetails,
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
      throws AuthenticationException {}
}
