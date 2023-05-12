package com.busa.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
@Slf4j
public class AmazonClientService {
    private AmazonS3 amazonS3;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

     @PostConstruct
    private  void initializeAmazon(){
         AWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKey,this.secretKey);
         this.amazonS3 = new AmazonS3Client(awsCredentials);

     }

    public String uploadFile(MultipartFile files) {
         String fileurls = "";
        File fileobj = convertMultiplepartsfile(files);
         String filename = System.currentTimeMillis()+"_"+files.getOriginalFilename();
         amazonS3.putObject(new PutObjectRequest(bucketName,filename,fileobj));
         fileurls = endpointUrl + "/" + bucketName + "/" + filename;
         fileobj.delete();
        return "File Added: " + fileurls;
    }

    private File convertMultiplepartsfile(MultipartFile file){
         File convertedFile = new File(file.getOriginalFilename());
         try (FileOutputStream fileOutputStream = new FileOutputStream(convertedFile)){
             fileOutputStream.write(file.getBytes());

         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
         return convertedFile;
    }
}
