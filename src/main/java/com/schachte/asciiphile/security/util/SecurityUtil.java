package com.schachte.asciiphile.security.util;

import java.util.function.Supplier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/** Static util class for grabbing username info */
public class SecurityUtil {

  private static Supplier<SecurityContext> securityContextSupplier =
      () -> SecurityContextHolder.getContext();

  public static Supplier<String> userSupplier =
      () -> securityContextSupplier.get().getAuthentication().getName();

  //  public static Supplier<String> passSupplier =
  //          () -> securityContextSupplier.get().getAuthentication().getCredentials();
}
