package com.schachte.asciiphile.security;

public class SecurityConstants {
  static final String SECRET = "SecretKeyToGenJWTs";
  static final long EXPIRATION_TIME = 864_000_000; // 10 days
  static final String TOKEN_PREFIX = "Bearer ";
  static final String HEADER_STRING = "Authorization";
}
