package com.schachte.asciiphile.controller;

import com.schachte.asciiphile.api.S3;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/phile")
public class UploadController {

  @Autowired private S3 s3Service;

  @PostMapping("/upload")
  public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file)
      throws IOException {
    return s3Service.upload(file);
  }

  @PostMapping("/presign")
  public String generatePresignedUrl(@RequestParam("objectKey") String objectKey) {
    return objectKey != null ? s3Service.preSign(objectKey) : "Error: Invalid Object Key";
  }
}
