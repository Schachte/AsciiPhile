package com.schachte.asciiphile.security.util;

import com.schachte.asciiphile.model.JwtAuthententicationToken;
import com.schachte.asciiphile.model.User;
import com.schachte.asciiphile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {
  @Autowired private UserRepo userRepo;

  public User validate(JwtAuthententicationToken userToken) {

    return null;
    //
    //        try {
    //            Claims body = Jwts.parser()
    //                    //TODO: Add environment based variable here!
    //                    .setSigningKey("secret")
    //                    .parseClaimsJws(token)
    //                    .getBody();
    //            jwtUser = new User();
    //            jwtUser.setUsername(body.getSubject());
    //            jwtUser.setPassword((String) body.get("password"));
    //        } catch (Exception e) {
    //            //TODO: Add logger here
    //            System.out.println(e);
    //        }
    //
    //        return jwtUser;
  }
}
