package com.example.licenta.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.example.licenta.utils.FileUtils.getFilePath;

@Service
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;


    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(String problemDir, File file){
        String fileName=getFilePath(problemDir, file.getName());
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
        file.delete();
        return "File uploaded : "+ fileName;
    }

    public String downloadFile(String problemDir, String fileName){
        String filePath = getFilePath(problemDir, fileName);

        S3Object s3Object = s3Client.getObject(bucketName, filePath);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        try{
            String fileText = IOUtils.toString(inputStream);
            return fileText;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
