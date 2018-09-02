package com.schachte.asciiphile.service;

import com.schachte.asciiphile.model.User;
import com.schachte.asciiphile.repository.UserRepo;
import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
    Optional<User> user = applicationUserRepository.findByUsername(username);

    User applicationUser =
        user.orElseThrow(() -> new UsernameNotFoundException("Error, username not found!"));

    return new org.springframework.security.core.userdetails.User(
        applicationUser.getUsername(), applicationUser.getPassword(), Collections.emptyList());
  }
}
