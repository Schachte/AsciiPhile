package com.schachte.asciiphile.service;

import com.schachte.asciiphile.model.CustomUserDetails;
import com.schachte.asciiphile.model.User;
import com.schachte.asciiphile.repository.UserRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserRepo applicationUserRepository;

  public UserDetailsServiceImpl(UserRepo applicationUserRepository) {
    this.applicationUserRepository = applicationUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> applicationUser = applicationUserRepository.findByUsername(username);
    if (!applicationUser.isPresent()) {
      throw new UsernameNotFoundException(username);
    }

    List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
    GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_ADMIN");
    auths.add(auth);

    return new CustomUserDetails(applicationUser.get(), auths);
  }
}
