package com.schachte.asciiphile.controller;

import static com.schachte.asciiphile.security.util.SecurityUtil.userSupplier;

import com.schachte.asciiphile.model.File;
import com.schachte.asciiphile.repository.FileRepo;
import com.schachte.asciiphile.repository.UserRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phile")
public class UserDataController {
  @Autowired UserRepo userRepository;
  @Autowired FileRepo fileRepository;

  @GetMapping("/files")
  public List<File> retrieveUserDirectoryFiles() {
    return fileRepository.findByUsername(userSupplier.get());
  }
}
