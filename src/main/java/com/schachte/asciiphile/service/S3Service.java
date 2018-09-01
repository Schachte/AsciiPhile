package com.schachte.asciiphile.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service implements S3 {

    private String bucketName;
    private String regionName;
    private String accessKey;
    private String secretKey;

    private final BasicAWSCredentials creds;
    private final AmazonS3 s3Client;

    private final BiFunction<String, String, Boolean> keysAreDefault = (k1, k2) -> (k1.equals("default") || k2.equals("default"));

    @Autowired
    public S3Service(@Value("${phile.accessSecret}") String secret, @Value("${phile.accessKey}") String access, @Value("${phile.regionName}") String region, @Value("${phile.bucketName}") String bucket) {
        this.bucketName = bucket;
        this.regionName = region;
        this.secretKey = secret;
        this.accessKey = access;

        creds = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .withRegion(regionName)
                .build();
    }

    @Override
    public String upload(MultipartFile file) throws IOException {

        if (keysAreDefault.apply(accessKey, secretKey)) {
            return "Error, keys are not set";
        }

        InputStream fileInputStream = file.getInputStream();
        s3Client.putObject(
                new PutObjectRequest(
                        bucketName, file.getOriginalFilename(), fileInputStream, new ObjectMetadata()));
        return "Upload Successful";
    }

    @Override
    public String preSign(String objectKey) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(generateExpiryValue());

        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    private Date generateExpiryValue() {
        Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 15000;
        expiration.setTime(expTimeMillis);
        return expiration;
    }
}
