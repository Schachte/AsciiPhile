package com.schachte.asciiphile.model;

import lombok.Data;

@Data
public class S3File {
  private String fileName;
  private String fileExtension;
  private String mimeType;
  private String description;
  private String s3Url;
}
