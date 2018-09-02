package com.schachte.asciiphile.repository;

import com.schachte.asciiphile.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String user);
}
