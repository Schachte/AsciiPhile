package com.schachte.asciiphile.controller;

import com.schachte.asciiphile.model.User;
import com.schachte.asciiphile.security.util.JwtGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {

  private JwtGenerator jwtGenerator;

  public TokenController(JwtGenerator jwtGenerator) {
    this.jwtGenerator = jwtGenerator;
  }

  @PostMapping
  public String generateJwtToken(@RequestBody final User user) {
    return jwtGenerator.generate(user);
  }
}
