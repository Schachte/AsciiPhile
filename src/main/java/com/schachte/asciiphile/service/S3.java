package com.schachte.asciiphile.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface S3 {
  String upload(MultipartFile file) throws IOException;

  String preSign(String objectKey);
}
