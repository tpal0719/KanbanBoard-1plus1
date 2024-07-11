package com.sparta.springtrello.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class S3Uploader {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    @Autowired
    public S3Uploader(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    /**
     * Uploads a file to the specified directory in S3.
     *
     * @param file    the file to upload
     * @param dirName the directory name
     * @return the URL of the uploaded file
     * @throws IOException if an I/O error occurs
     */
    public String upload(MultipartFile file, String dirName) throws IOException {
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (Exception e) {
            // Log error and rethrow
            throw new IOException("Failed to upload file to S3", e);
        }
    }

    /**
     * Downloads a file from S3.
     *
     * @param fileUrl the URL of the file to download
     * @return the resource representing the file content
     * @throws IOException if an I/O error occurs
     */
    public Resource download(String fileUrl) throws IOException {
        String key = extractKeyFromUrl(fileUrl);
        try {
            S3Object s3Object = amazonS3.getObject(bucketName, key);
            return new InputStreamResource(s3Object.getObjectContent());
        } catch (Exception e) {
            // Log error and rethrow
            throw new IOException("Failed to download file from S3", e);
        }
    }

    /**
     * Deletes a file from S3.
     *
     * @param fileUrl the URL of the file to delete
     */
    public void delete(String fileUrl) {
        String key = extractKeyFromUrl(fileUrl);
        amazonS3.deleteObject(bucketName, key);
    }

    /**
     * Checks if an object exists in S3.
     *
     * @param fileUrl the URL of the file to check
     * @return true if the object exists, false otherwise
     */
    public boolean doesObjectExist(String fileUrl) {
        String key = extractKeyFromUrl(fileUrl);
        return amazonS3.doesObjectExist(bucketName, key);
    }

    /**
     * Extracts the S3 object key from the file URL.
     *
     * @param fileUrl the file URL
     * @return the object key
     */
    private String extractKeyFromUrl(String fileUrl) {
        String bucketUrlPrefix = String.format("https://%s.s3.%s.amazonaws.com/", bucketName, amazonS3.getRegionName());
        return fileUrl.replace(bucketUrlPrefix, "");
    }
}
