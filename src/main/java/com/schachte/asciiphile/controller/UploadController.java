package com.schachte.asciiphile.controller;

import com.schachte.asciiphile.api.S3;
import com.schachte.asciiphile.model.User;
import com.schachte.asciiphile.repository.UserRepo;
import com.schachte.asciiphile.service.UserDetailsServiceImpl;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/phile")
public class UploadController {

  @Autowired private S3 s3Service;

  @Autowired private UserDetailsServiceImpl userDetailsService;

  @Autowired private UserRepo userRepo;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @PostMapping("/upload")
  public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file)
      throws IOException {
    return s3Service.upload(file);
  }

  @PostMapping("/auth")
  public String tester() {
    User usr = new User();
    usr.setUsername("schachte");
    usr.setPassword(bCryptPasswordEncoder.encode("schachte"));
    userRepo.save(usr);
    return "saved";
  }

  @PostMapping("/presign")
  public String generatePresignedUrl(@RequestParam("objectKey") String objectKey) {
    return objectKey != null ? s3Service.preSign(objectKey) : "Error: Invalid Object Key";
  }
}
