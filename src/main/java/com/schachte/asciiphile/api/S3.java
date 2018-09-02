package com.schachte.asciiphile.api;

import java.io.IOException;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface S3 {
  Map<String, String> upload(MultipartFile file) throws IOException;

  String preSign(String objectKey);
}
