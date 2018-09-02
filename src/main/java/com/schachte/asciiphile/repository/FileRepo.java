package com.schachte.asciiphile.repository;

import com.schachte.asciiphile.model.File;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<File, Integer> {
  List<File> findByUsername(String username);
}
