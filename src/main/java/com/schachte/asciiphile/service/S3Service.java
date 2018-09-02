package com.schachte.asciiphile.service;

import static com.schachte.asciiphile.security.util.SecurityUtil.userSupplier;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.schachte.asciiphile.api.S3;
import com.schachte.asciiphile.model.File;
import com.schachte.asciiphile.repository.FileRepo;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service implements S3 {

  @Autowired private FileRepo fileRepo;

  private String bucketName;
  private String regionName;
  private String accessKey;
  private String secretKey;

  private final BasicAWSCredentials creds;
  private final AmazonS3 s3Client;

  @Autowired
  public S3Service(
      @Value("${phile.accessSecret:default}") String secret,
      @Value("${phile.accessKey:default}") String access,
      @Value("${phile.regionName:default}") String region,
      @Value("${phile.bucketName:default}") String bucket) {
    this.bucketName = bucket;
    this.regionName = region;
    this.secretKey = secret;
    this.accessKey = access;

    creds = new BasicAWSCredentials(accessKey, secretKey);
    s3Client =
        AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(creds))
            .withRegion(regionName)
            .build();
  }

  @Override
  public Map<String, String> upload(MultipartFile file) throws IOException {
    InputStream fileInputStream = file.getInputStream();
    s3Client.putObject(
        new PutObjectRequest(
            bucketName, generateFileName(file), fileInputStream, new ObjectMetadata()));

    fileRepo.save(generateFile(file));

    Map<String, String> respMap = new HashMap<>();
    respMap.put("fileName", file.getOriginalFilename());
    respMap.put("bytes", String.valueOf(file.getSize()));
    return respMap;
  }

  @Override
  public String preSign(String objectKey) {
    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(bucketName, objectKey)
            .withMethod(HttpMethod.GET)
            .withExpiration(generateExpiryValue());
    return s3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
  }

  private File generateFile(MultipartFile file) {
    File newFile = new File();
    newFile.setExtension(generateExtension(file));
    newFile.setName(generateFileName(file));
    newFile.setUsername(userSupplier.get());
    return newFile;
  }

  private String generateFileName(MultipartFile file) {
    StringBuilder builder = new StringBuilder();
    builder.append(userSupplier.get());
    builder.append("-");
    builder.append(file.getOriginalFilename());
    return builder.toString();
  }

  private String generateExtension(MultipartFile file) {
    List<String> fileSplit = Arrays.asList(file.getOriginalFilename().split("\\."));
    return fileSplit.get(fileSplit.size() - 1);
  }

  private Date generateExpiryValue() {
    final int fifteen_seconds = 15000;
    Date expiration = new java.util.Date();
    long expTimeMillis = expiration.getTime();
    expTimeMillis += fifteen_seconds;
    expiration.setTime(expTimeMillis);
    return expiration;
  }
}
